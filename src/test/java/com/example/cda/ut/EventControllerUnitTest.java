package com.example.cda.ut;

import com.example.cda.controllers.user.EventController;
import com.example.cda.dtos.EventDto;
import com.example.cda.exceptions.NatureNotFoundException;
import com.example.cda.exceptions.SpaceNotFoundException;
import com.example.cda.exceptions.SubjectNotFoundException;
import com.example.cda.modeles.*;
import com.example.cda.services.*;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URISyntaxException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerUnitTest {
/*
    private MockMvc mockMvc;
    @Autowired
    EventController eventController;

    @MockBean
    JwtUserService mockUserService;
    @MockBean
    SpaceService mockspaceService;
    @MockBean
    SubSubjectService mockSubSubjectService;
    @MockBean
    NatureActionService mockNatureActionService;
    @MockBean
    DoctorUserService mockDoctorService;
    @MockBean
    EventService mockEventService;
    @MockBean
    MedicalSpecialtiesService mockMedicalSpecialtiesService;
    @MockBean
    ConsultationService mockConsultationService;


    @BeforeEach
    public void setup() {

        User user=  new User();
        user.setId(1);
        user.setUsername("maroua@gmail.com");
        user.setPassword("123");

        UserDetails userDetails = user;
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @WithMockUser
    public void testSave() throws SubjectNotFoundException, URISyntaxException, NatureNotFoundException, SpaceNotFoundException, ParseException {
        //preparer les testes
        Long idSpace = 1L;
        Long idSubject= 1L;
        Long idSubSubject = 1L;
        User user=  new User();
        user.setId(1);
        NatureAction natureAction= Mockito.mock(NatureAction.class);
        natureAction.setId(1);
        SubSubject subSubject = new SubSubject();
        subSubject.setId(1L);
        DoctorUser doctorUser= new DoctorUser();
        doctorUser.setId(1L);
        MedicalSpecialties medicalSpecialties= new MedicalSpecialties();
        medicalSpecialties.setId(1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");

        Event event= new Event();
        event.setDate(date);
        event.setId(1L);
        Consultation consultation= new Consultation(event, doctorUser,medicalSpecialties);

        EventDto dto = new EventDto();
        Mockito.when(mockUserService.loadUserByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockNatureActionService.get(ArgumentMatchers.anyInt())).thenReturn(natureAction);
        Mockito.when(natureAction.getTitle()).thenReturn("Consultation");
        Mockito.when(mockSubSubjectService.get(ArgumentMatchers.anyLong())).thenReturn(subSubject);
        Mockito.when(mockEventService.validInformation(ArgumentMatchers.any(User.class),ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong())).thenReturn(true);
        Mockito.when((mockDoctorService.get(ArgumentMatchers.anyLong()))).thenReturn(doctorUser);
        Mockito.when(mockMedicalSpecialtiesService.get(ArgumentMatchers.anyInt())).thenReturn(medicalSpecialties);
       Mockito.when((mockConsultationService.get(ArgumentMatchers.anyLong()))).thenReturn(consultation);
        //appeler la methode à tester

        Event result = eventController.save(dto,ArgumentMatchers.any(Principal.class),idSpace, idSubject, idSubSubject).getBody();
        //Assertions.assertEquals("2023-05-15 11:15", format.format(result.getDate()));

    }
*/

}
