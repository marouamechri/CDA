package com.example.cda.it;

import com.example.cda.modeles.User;
import com.example.cda.repositorys.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserRepository mockUserRepository;

    @Test
    public void testRegister() throws Exception {
        //Defining the mock with Mockito
        User user = new User();
        user.setId(1);
        user.setUsername("marouaTest@gmail.com");
        user.setName("maroua");
        Mockito.when(mockUserRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(null);
        Mockito.when(mockUserRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        //Testing
        String requestBody = "{" +
                "\"username\":\"marouaTest@gmail.com\"," +
                "\"password\":\"1234\"," +
                "\"name\":\"maroua\"" +
                "}";
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"accessToken\":") && responseBody.contains("\"name\":\"maroua\""));
    }
    @Test
    public void testRegisterWithAccountAlreadyExist() throws Exception {
        //Defining the mock with Mockito
        User user = new User();
        user.setId(1);
        user.setUsername("TestUser@gmail.com");
        Mockito.when(mockUserRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        //Testing
        String requestBody = "{" +
                "\"username\":\"TestUser@gmail.com\"," +
                "\"password\":\"1234\"," +
                "\"firstname\":\"maroua\"" +
                "}";
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn();
    }

}
