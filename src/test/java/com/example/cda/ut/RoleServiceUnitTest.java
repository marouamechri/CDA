package com.example.cda.ut;

import com.example.cda.modeles.Role;
import com.example.cda.repositorys.RoleRepository;
import com.example.cda.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleServiceUnitTest {
    @Autowired
    RoleService roleService;
    @MockBean
    RoleRepository mockRoleRepository;
    @Mock
    UserDetails userDetails;
    @Test
    public void testCreateRole() {
        //Define the Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("RoleTest");

        Mockito.when(mockRoleRepository.findByName(ArgumentMatchers.anyString())).thenReturn(null);
        Mockito.when(mockRoleRepository.save(ArgumentMatchers.any(Role.class))).thenReturn(role);

        //Assertions
        Role result = roleService.create("Role");
        Assertions.assertEquals("RoleTest", result.getAuthority());
    }
    @Test
    public void testCreateRoleAlreadyExist() {
        //Define the Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("RoleTest");

        Mockito.when(mockRoleRepository.findByName(ArgumentMatchers.anyString())).thenReturn(role);

        //Assertions
        Role result = roleService.create("Role");
        Assertions.assertEquals(null, result);
    }

    @Test
    public void testGetListRole() {
        //Define the mockito
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("RoleTest");
        roles.add(role);

        Mockito.when(mockRoleRepository.findAll()).thenReturn(roles);

        //Assertions
        Iterable<Role> results = roleService.list();
        Assertions.assertIterableEquals(roles, results);
    }
    @Test
    public void testGetRole() {
        //Define the Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("RoleTest");

        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));

        //Assertions
        Role result = roleService.get(9);
        Assertions.assertEquals("RoleTest", result.getAuthority());
    }
    @Test
    public void testGetRoleNotFound() {
        //Define the Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        //Assertions
        Role result = roleService.get(9);
        Assertions.assertEquals(null,result);
    }


}
