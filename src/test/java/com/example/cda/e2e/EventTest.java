package com.example.cda.e2e;

import com.example.cda.modeles.*;
import com.example.cda.services.*;
import com.example.cda.services.impl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser()
    public void testSaveEventURLNotValidInformation() throws Exception {

        String requestBody = "{\"date\":\"2023-05-15 11:15\",\"validate\":\"\",\"natureAction\":\"1\",\"doctor\":\"1\",\"medicalSpecialties\":\"1\"}";
        User user = new User();
        Long idSpace = 1L;
        Long idSubject = 2L;
        Long idSubSubject = 3L;


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/spaces/"+idSpace+"/subject/"+idSubject+"/subSubject/" +idSubSubject+"/event").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertFalse(responseBody.contains("\"date\":\"2023-05-15 11:15\""));

    }

   /* @Test
    @WithMockUser(username = "maroua@gmail.com", authorities = "USER")
    public void testSaveEvent() throws Exception {
        String requestBody = "{\"date\":\"2023-05-15 11:15\",\"validate\":\"\",\"natureAction\":\"1\",\"doctor\":\"1\",\"medicalSpecialties\":\"1\"}";
        User user = new User();
        Long idSpace = 1L;
        Long idSubject = 2L;
        Long idSubSubject = 3L;


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/spaces/"+idSpace+"/subject/"+idSubject+"/subSubject/" +idSubSubject+"/event").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"date\":\"2023-05-15 11:15\""));


    }*/



}
