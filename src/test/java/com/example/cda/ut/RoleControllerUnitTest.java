package com.example.cda.ut;

import com.example.cda.controllers.admin.RoleController;
import com.example.cda.dtos.RoleDto;
import com.example.cda.exceptions.RoleExistException;
import com.example.cda.exceptions.UserNotFoundException;
import com.example.cda.modeles.Role;
import com.example.cda.modeles.User;
import com.example.cda.services.JwtUserService;
import com.example.cda.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = {"ADMIN"})
public class RoleControllerUnitTest {
    @Autowired
    RoleController roleController;
    @MockBean
    RoleService mockRoleService;

    @MockBean
    JwtUserService mockUserService;
    @Test
    public void testGetList() {
        //Defining the mock with Mockito
        List<Role> roles = new ArrayList<>();

        Mockito.when(mockRoleService.list()).thenReturn(roles);

        //Testing
        Iterable<Role> results = roleController.list().getBody();
        Assertions.assertIterableEquals(roles, results);
    }
    @Test
    public void testCreateRole() throws Exception {
        //Defining the mock with Mockito
        RoleDto dto = new RoleDto();
        dto.setName("RoleTest");
        Role role = new Role();
        role.setId(1);
        role.setName("RoleTest");

        Mockito.when(mockRoleService.create(ArgumentMatchers.anyString())).thenReturn(role);

        //Testing
        Role result = roleController.create(dto).getBody();
        assert result != null;
        Assertions.assertEquals("RoleTest", result.getAuthority());
    }
    @Test
    public void testCreateRoleAlreadyExist() {
        //Defining the mock with Mockito
        Mockito.when(mockRoleService.create(ArgumentMatchers.anyString())).thenThrow(RoleExistException.class);

        //Testing
        Assertions.assertThrows(RoleExistException.class, () -> {
            RoleDto dto = new RoleDto();
            dto.setName("RoleTest");
            roleController.create(dto);
        });
    }
    @Test
    public void testDeleteRole() {
        Object result = roleController.delete(9).getBody();
        Assertions.assertNull(result);
    }

}
