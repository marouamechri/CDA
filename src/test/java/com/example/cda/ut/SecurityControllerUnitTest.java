package com.example.cda.ut;

import com.example.cda.controllers.SecurityController;
import com.example.cda.dtos.AuthRequestDto;
import com.example.cda.dtos.AuthResponseDto;
import com.example.cda.exceptions.AccountExistsException;
import com.example.cda.modeles.User;
import com.example.cda.services.JwtUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityControllerUnitTest {
    @Autowired
    SecurityController securityController;
    @MockBean
    JwtUserService mockJwtUserService;
    @Test
    public void testRegister() {
        //un objet AuthRequestDto pour simuler la demande d'inscription
        AuthRequestDto dto = new AuthRequestDto();
        dto.setUsername("testing@gmail.com");
        dto.setPassword("testing");
        dto.setName("testing");

        User user = new User();
        user.setId(1);
        user.setUsername("UserTest");
        String jwt = "jwtTokenForTesting";


        Mockito.when(mockJwtUserService.findByUserName(ArgumentMatchers.anyString())).thenReturn(null);
        Mockito.when(mockJwtUserService.save(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockJwtUserService.generateJwtForUser(ArgumentMatchers.any(UserDetails.class))).thenReturn(jwt);
        //Testing
        AuthResponseDto result = securityController.register(dto).getBody();
        assert result != null;
        System.out.println("test getAccessToken : "+result.getAccessToken());
        Assertions.assertEquals("jwtTokenForTesting", result.getAccessToken());

    }
    @Test
    public void testRegisterWithUserAlreadyExist() {

        User user = new User();
        user.setId(1);
        user.setUsername("UserTest");
        String jwt = "jwtTokenForTesting";

        Mockito.when(mockJwtUserService.findByUserName(ArgumentMatchers.anyString())).thenReturn(user);

        //Testing
        Assertions.assertThrows(AccountExistsException.class, () -> {
            AuthRequestDto dto = new AuthRequestDto();
            dto.setUsername("testing");
            dto.setPassword("testing");
            dto.setName("testing");
            securityController.register(dto);
        });
    }
    @Test
    public void testAuthorize() throws Exception {
        //Defining the mock with Mockito
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername("UserTest@gmail.com");
        authRequestDto.setPassword("testingPassword");
        User user = new User();
        user.setId(1);
        user.setUsername("Test");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "HelloWorld");

        String jwt = "jwtTokenForTesting";

        Mockito.when(mockJwtUserService.authenticate(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(authentication);
        Mockito.when(mockJwtUserService.generateJwtForUser(ArgumentMatchers.any(UserDetails.class))).thenReturn(jwt);

        //Testing
        AuthResponseDto result = securityController.login(authRequestDto).getBody();
        assert result != null;
        Assertions.assertEquals("jwtTokenForTesting", result.getAccessToken());
    }
}
