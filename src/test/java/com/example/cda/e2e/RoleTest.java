package com.example.cda.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "maroua@gmail.com", authorities = {"USER", "ADMIN"})
public class RoleTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    public void getRoleList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/admin/roles")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertFalse(responseBody.contains("[]"));
    }
    @Test
    public void testCreateRole() throws Exception {
        String requestBody = "{\"name\":\"TEST\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/roles").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"authority\":\"TEST\""));
    }
    @Test
    public void testDeleteRoleNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/roles/12/users/0/detach")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

    }
}
