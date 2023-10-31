package com.example.cda.it;

import com.example.cda.modeles.Role;
import com.example.cda.modeles.User;
import com.example.cda.repositorys.RoleRepository;
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
    public void testAttachRoleToUser() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_TEST");
        Collection<Role> roles = new ArrayList<>();
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));
        User user = new User();
        user.setId(1);
        user.setUsername("UserTest");
        user.setRoles(roles);
        Mockito.when(mockUserRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(user));
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/admin/roles/1/users/1/attach"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"username\":\"UserTest\"") && responseBody.contains("\"authority\":\"ROLE_TEST\""));
    }
    @Test
    public void testAttachRoleNotFoundToUser() throws Exception {
        //Defining the mock with Mockito
        User user = new User();
        Collection<Role> roles = new ArrayList<>();//Roles already attached to user
        user.setId(1);
        user.setUsername("UserTest");
        user.setRoles(roles);
        Mockito.when(mockUserRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Testing
        mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/admin/roles/1/users/1/attach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

    }
    @Test
    public void testDetachRoleToUser() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_TEST");
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));
        User user = new User();
        user.setId(1);
        user.setUsername("UserTest");
        user.setRoles(roles);
        Mockito.when(mockUserRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(user));
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/admin/roles/12/users/13/detach")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"username\":\"UserTest\"") && responseBody.contains("\"authorities\":[]"));
    }
    @Test
    public void testDetachRoleToUserNotFound() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_TEST");
        Mockito.when(mockUserRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));
        //Testing
        mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/admin/roles/12/users/13/detach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
    @Test
    public void testDetachRoleNotFoundToUser() throws Exception {
        //Defining the mock with Mockito
        User owner = new User();
        Collection<Role> roles = new ArrayList<>();//Roles already attached to user
        owner.setId(1);
        owner.setUsername("UserTest");
        owner.setRoles(roles);
        Mockito.when(mockUserRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(owner));
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Testing
        mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/admin/roles/12/users/13/detach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

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
