package com.example.cda.ut;

import com.example.cda.modeles.User;
import com.example.cda.repositorys.UserRepository;
import com.example.cda.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceUnitTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository mockUserRepository;
    @MockBean
    AuthenticationConfiguration mockAuthenticationConfiguration;
    @MockBean
    AuthenticationManager mockAuthenticationManager;
    @Test
    public void testAuthenticate() throws Exception {
        //Defining the mock with Mockito
        Authentication authentication = new UsernamePasswordAuthenticationToken("Hello", "Word");
        Mockito.when(mockAuthenticationConfiguration.getAuthenticationManager()).thenReturn(mockAuthenticationManager);
        Mockito.when(mockAuthenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(authentication);

        //Assertions
        Authentication result = userService.authenticate("test@gmail.com", "123456!");
        Assertions.assertEquals("Word", result.getCredentials());
    }
    @Test
    public void testSave() {
        //Defining the mock with Mockito
        User user = new User();
        user.setId(1);
        user.setUsername("test@gmail.com");

        Mockito.when(mockUserRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(null);
        Mockito.when(mockUserRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        //Assertions
        UserDetails result = userService.save("jean.michel@gmail.com", "Azerty1234!", "maroua");
        Assertions.assertEquals("test@gmail.com", result.getUsername());
    }
    @Test
    public void testLoadByUsername() {
        //Define the Mockito
        User user = new User();
        user.setId(1);
        user.setUsername("test@gmail.com");

        Mockito.when(mockUserRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(user);

        //Assertions
        UserDetails result = userService.loadUserByUsername("World");
        Assertions.assertEquals("test@gmail.com", result.getUsername());
    }
    @Test
    public void testLoadByUsernameNotFound() {
        //Define the Mockito
        Mockito.when(mockUserRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(null);

        //Assertions
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("HelloWorld"));
    }


}
