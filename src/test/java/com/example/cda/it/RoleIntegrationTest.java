package com.example.cda.it;

import com.example.cda.modeles.Role;
import com.example.cda.modeles.User;
import com.example.cda.repositorys.RoleRepository;
import com.example.cda.repositorys.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "ADMIN")
public class RoleIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserRepository mockUserRepository;
    @MockBean
    RoleRepository mockRoleRepository;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetRoleList() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_TEST");
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        Mockito.when(mockRoleRepository.findAll()).thenReturn(roles);
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/dashboard/admin/roles")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"authority\":\"ROLE_TEST\""));
    }
    @Test
    public void testCreateRole() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_TEST");
        Mockito.when(mockRoleRepository.save(ArgumentMatchers.any(Role.class))).thenReturn(role);
        //Testing
        String requestBody = "{\"name\":\"ROLE_TEST\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/admin/roles").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"authority\":\"ROLE_TEST\""));
    }
    @Test
    public void testDeleteRole() throws Exception {
        //Defining the mock with Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(new Role()));
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/dashboard/admin/roles/3")).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }
    @Test
    public void testDeleteRoleNotFound() throws Exception {
        //Defining the mock with Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/dashboard/admin/roles/3")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

    }
}
