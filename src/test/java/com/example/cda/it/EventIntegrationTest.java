package com.example.cda.it;

import com.example.cda.dtos.EventDto;
import com.example.cda.modeles.*;
import com.example.cda.repositorys.*;
import com.example.cda.services.impl.*;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    EventRepository mockEventRepository;
    @MockBean
    NatureActionRepository mockNatureActionRepository;
    @MockBean
    SubSubjectRepository mockSubSubjectRepository;
    @MockBean
    DoctorUserRepository mockDoctorUserRepository;
    @MockBean
    TaskRepository mockTaskRepository;
    @MockBean
    MedicalSpecialtiesRepository mockMedicalSpecialtiesRepository;
    @MockBean
    ConsultationRepository mockConsultationRepository;
    @MockBean
    TreatmentRepository mockTreatmentRepository;
    @MockBean
    SubjectRepository mockSubjectRepository;
    @MockBean
    AnalysisRepository mockAnalyseRepository;
    @MockBean
    SpaceRepository mockSpaceRepository;
    @MockBean
    UserRepository mockuserRepository;
    @MockBean
    EventServiceImpl mockEventService;
    @MockBean
    SpaceServiceImpl mockSpaceService;
    @MockBean
    SubSubjectServiceImp mockSubSubjectServiceImp;
    @MockBean
    NatureActionServiceImpl mockNatureActionService;
    @MockBean
    JwtUserServiceImpl mockUserService;


    @MockBean
    SubSubject subSubject;
    @MockBean
    Space space;
    @MockBean
    NatureAction natureAction;
    @MockBean
    MedicalSpecialties medicalSpecialties;
    @MockBean
    DoctorUser doctor;
    @MockBean
    Event event;
    @MockBean
    Subject subject;

    @Test
    @WithMockUser(username = "maroua@gmail.com", authorities = "USER")
    public void testSaveEventConsultation() throws Exception {
        //preparer les données de test
        String requestBody = "{\"date\":\"2020-05-15 11:15\",\"validate\":\"\",\"natureAction\":\"1\",\"doctor\":\"1\",\"medicalSpecialties\":\"1\"}";
        User user = new User();
        user.setId(1);
        user.setUsername("maroua@gmail.com");

        Long idSpace = 1L;
        Long idSubject = 2L;
        Long idSubSubject = 3L;

        space.setId(1L);

        subject.setId(2L);

        natureAction.setId(1);
        natureAction.setTitle("Consultation");

        subSubject.setId(3L);
        subSubject.setTitle("testSubSubject");

        doctor.setId(1L);
        doctor.setName("Stella");

        MedicalSpecialties medicalSpecialties = new MedicalSpecialties("generaliste");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-10-09 11:15");
        event.setId(1L);
        event.setDate(date);
        event.setNatureAction(natureAction);

        Consultation consultation = Mockito.mock(Consultation.class);
        consultation.setId(1L);
        consultation.setDate(date);

        Mockito.when(mockUserService.loadUserByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockNatureActionService.get(ArgumentMatchers.anyInt())).thenReturn(natureAction);
        Mockito.when(natureAction.getTitle()).thenReturn("Consultation");
        Mockito.when(mockSubSubjectServiceImp.get(ArgumentMatchers.anyLong())).thenReturn(subSubject);
        Mockito.when(mockEventService.validInformation(user,idSpace,idSubject , idSubSubject )).thenReturn(true);

        Mockito.when(mockDoctorUserRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(doctor));
        Mockito.when(mockMedicalSpecialtiesRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(medicalSpecialties));
        Mockito.when(mockSpaceRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(space));
        Mockito.when(mockSubjectRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(mockConsultationRepository.save(ArgumentMatchers.any(Consultation.class))).thenReturn(consultation);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/user/spaces/"+idSpace+"/subject/"+idSubject+"/subSubject/"
                                +idSubSubject+"/event").contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        //Assertions.assertTrue(responseBody.contains("\"date\":\"2023-10-09 11:15\""));
        //Mockito.verify(mockConsultationRepository).save(consultation);


    }
    @Test
    @WithMockUser(username = "maroua@gmail.com", authorities = "USER")
    public void testSaveEventAnalyse() throws Exception {

        MockitoAnnotations.initMocks(this);

        String requestBody = "{\"date\":\"2020-05-15 11:15\",\"validate\":\"\",\"natureAction\":\"1\",\"doctor\":\"\",\"medicalSpecialties\":\"\"}";

        User user = new User();
        user.setId(1);
        user.setUsername("maroua@gmail.com");

        Long idSpace = 1L;
        Long idSubject = 2L;
        Long idSubSubject = 3L;
        space.setId(1L);
        subject.setId(2L);
        natureAction.setId(1);
        natureAction.setTitle("Analyse");

        subSubject.setId(3L);
        subSubject.setTitle("testSubSubject");

        Mockito.when(mockEventService.validInformation(user,idSpace,idSubject , idSubSubject )).thenReturn(true);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-10-09 11:15");
        event.setId(1L);
        event.setDate(date);
        event.setNatureAction(natureAction);

        Analysis analysis = new Analysis(event);

        Mockito.when(mockUserService.loadUserByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockNatureActionService.get(ArgumentMatchers.anyInt())).thenReturn(natureAction);
        Mockito.when(natureAction.getTitle()).thenReturn("Analyse");
        Mockito.when(mockSubSubjectServiceImp.get(ArgumentMatchers.anyLong())).thenReturn(subSubject);
        Mockito.when(mockEventService.validInformation(user,idSpace,idSubject , idSubSubject )).thenReturn(true);

        Mockito.when(mockSubSubjectRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(subSubject));
        Mockito.when(mockSpaceRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(space));
        Mockito.when(mockSubjectRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(mockAnalyseRepository.save(ArgumentMatchers.any(Analysis.class))).thenReturn(analysis);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/spaces/"+idSpace+"/subject/"+idSubject+"/subSubject/"
                                +idSubSubject+"/event").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        //Assertions.assertTrue(responseBody.contains("2023-10-09 11:15"));

    }
    @Test
    @WithMockUser(username = "maroua@gmail.com", authorities = "USER")
    public void testSaveEventTreatment() throws Exception {

        MockitoAnnotations.initMocks(this);

        String requestBody = "{\"date\":\"2020-05-15 11:15\",\"validate\":\"\",\"natureAction\":\"1\",\"doctor\":\"\",\"dateFin\":\"2023-10-09 11:15\",\"medicalSpecialties\":\"\"}";

        User user = new User();
        user.setId(1);
        user.setUsername("maroua@gmail.com");

        Long idSpace = 1L;
        Long idSubject = 2L;
        Long idSubSubject = 3L;
        space.setId(1L);
        subject.setId(2L);
        natureAction.setId(1);
        natureAction.setTitle("Traitement");

        subSubject.setId(3L);
        subSubject.setTitle("testSubSubject");

        Mockito.when(mockEventService.validInformation(user,idSpace,idSubject , idSubSubject )).thenReturn(true);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-10-09 11:15");
        event.setId(1L);
        event.setDate(date);
        event.setNatureAction(natureAction);

        Treatment treatment = new Treatment(event, date);

        Mockito.when(mockUserService.loadUserByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockNatureActionService.get(ArgumentMatchers.anyInt())).thenReturn(natureAction);
        Mockito.when(natureAction.getTitle()).thenReturn("Consultation");
       Mockito.when(mockSubSubjectServiceImp.get(ArgumentMatchers.anyLong())).thenReturn(subSubject);
        Mockito.when(mockEventService.validInformation(user,idSpace,idSubject , idSubSubject )).thenReturn(true);
        Mockito.when(mockSpaceRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(space));
        Mockito.when(mockSubjectRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(subject));
        Mockito.when(mockTreatmentRepository.save(ArgumentMatchers.any(Treatment.class))).thenReturn(treatment);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/spaces/"+idSpace+"/subject/"+idSubject+"/subSubject/"
                                +idSubSubject+"/event").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        //Assertions.assertTrue(responseBody.contains("2023-10-09 11:15"));

    }


    @Test
    @WithMockUser(username = "maroua@gmail.com", authorities = "USER")
    public void testGetEvent() throws Exception {

        MockitoAnnotations.initMocks(this);

        Long idEvent = 1L;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        event.setId(1L);
        event.setDate(date);
        natureAction.setId(1);

        Analysis analysis = new Analysis(event);
        Mockito.when(mockNatureActionRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(natureAction));
        Mockito.when(natureAction.getTitle()).thenReturn("Analyse");
        Mockito.when(mockEventRepository.findById(idEvent)).thenReturn(Optional.of(event));
        Mockito.when(mockAnalyseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(analysis));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/event/"+idEvent).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }
    @Test
    @WithMockUser()
    public void testGetEventNotFound() throws Exception {


        Long idEvent = 1L;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        event.setId(1L);
        event.setDate(date);
        event.setSubSubject(ArgumentMatchers.any(SubSubject.class));
        Analysis analysis = new Analysis(event);

        Mockito.when(event.getNatureAction()).thenReturn(natureAction);
        Mockito.when(mockEventRepository.findById(idEvent)).thenReturn(null);
        Mockito.when(mockAnalyseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(analysis));

        assert event!=null;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/event/"+idEvent).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertFalse(responseBody.contains("\"date\":\"2023-05-15 11:15\""));

    }

    @Test
    @WithMockUser(username = "maroua@gmail.com", authorities = "USER")
    public  void testGetAllEventByUser() throws Exception {

        MockitoAnnotations.initMocks(this);
        //preparer le test
        User user = Mockito.mock(User.class);
        user.setId(1);
        user.setUsername("maroua@gmail.com");

        natureAction.setId(1);
        natureAction.setTitle("Analyse");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        event.setId(1L);
        event.setDate(date);
        event.setNatureAction(natureAction);

        space.setId(1L);
        space.setUser(user);

        Analysis analysis = new Analysis(event);

        List<Event> events = new ArrayList<>();
        events.add(analysis);

        subject.setId(1L);
        subSubject.setId(1L);
       // Mockito.when(natureAction.getTitle()).thenReturn("Analyse");
        Mockito.when(space.getUser()).thenReturn(user);
        Mockito.when(user.getSpaces()).thenReturn(List.of(space));
        Mockito.when(subject.getSubSubjects()).thenReturn(List.of(subSubject));
        Mockito.when(subSubject.getEvents()).thenReturn(events);
        Mockito.when(mockNatureActionRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(natureAction));
        Mockito.when(mockEventRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(event));
        Mockito.when(mockAnalyseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(analysis));
        Mockito.when(mockSpaceRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(space));
        Mockito.when(mockuserRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(event.getNatureAction()).thenReturn(natureAction);
        Mockito.when(event.getNatureAction().getTitle()).thenReturn("Analyse");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/event").param("isActive", String.valueOf(true)).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();

    }
    @Test
    @WithMockUser
    public void testGetAllEventBySubject() throws Exception {
        MockitoAnnotations.initMocks(this);
        //preparer le test
        Long idSpace =1L;
        Long idSubject = 1L;
        User user = Mockito.mock(User.class);
        user.setId(1);
        user.setUsername("maroua@gmail.com");

        natureAction.setId(1);
        natureAction.setTitle("Analyse");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        event.setId(1L);
        event.setDate(date);
        event.setNatureAction(natureAction);

        space.setId(1L);
        space.setUser(user);

        Analysis analysis = new Analysis(event);

        List<Event> events = new ArrayList<>();
        events.add(analysis);

        subject.setId(1L);
        subSubject.setId(1L);
        // Mockito.when(natureAction.getTitle()).thenReturn("Analyse");
        Mockito.when(space.getUser()).thenReturn(user);
        Mockito.when(user.getSpaces()).thenReturn(List.of(space));
        Mockito.when(subject.getSubSubjects()).thenReturn(List.of(subSubject));
        Mockito.when(subSubject.getEvents()).thenReturn(events);
        Mockito.when((user.getId())).thenReturn(1);
        Mockito.when(space.getUser().getId()).thenReturn(1);
        Mockito.when(mockNatureActionRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(natureAction));
        Mockito.when(mockEventRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(event));
        Mockito.when(mockAnalyseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(analysis));
        Mockito.when(mockSpaceRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(space));
        Mockito.when(mockuserRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockSpaceService.get(ArgumentMatchers.anyLong())).thenReturn(space);
        Mockito.when(mockUserService.loadUserByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockSpaceService.subjectExistSpace(space, idSubject)).thenReturn(true);
        Mockito.when(event.getNatureAction()).thenReturn(natureAction);
        Mockito.when(event.getNatureAction().getTitle()).thenReturn("Analyse");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/spaces/"+idSpace+"/subject/"+idSubject+"/event").param("isActive", String.valueOf(true)).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser
    public void testGetAllEventBySubSubject() throws Exception {
        MockitoAnnotations.initMocks(this);
        //preparer le test
        Long idSpace =1L;
        Long idSubject = 1L;
        Long idSubSubject = 1L;
        User user = Mockito.mock(User.class);
        user.setId(1);
        user.setUsername("maroua@gmail.com");

        natureAction.setId(1);
        natureAction.setTitle("Analyse");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        event.setId(1L);
        event.setDate(date);
        event.setNatureAction(natureAction);

        space.setId(1L);
        space.setUser(user);

        Analysis analysis = new Analysis(event);

        List<Event> events = new ArrayList<>();
        events.add(analysis);

        subject.setId(1L);
        subSubject.setId(1L);
        // Mockito.when(natureAction.getTitle()).thenReturn("Analyse");
        Mockito.when(space.getUser()).thenReturn(user);
        Mockito.when(user.getSpaces()).thenReturn(List.of(space));
        Mockito.when(subject.getSubSubjects()).thenReturn(List.of(subSubject));
        Mockito.when(subSubject.getEvents()).thenReturn(events);
        Mockito.when((user.getId())).thenReturn(1);
        Mockito.when(space.getUser().getId()).thenReturn(1);
        Mockito.when(mockNatureActionRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(natureAction));
        Mockito.when(mockEventRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(event));
        Mockito.when(mockAnalyseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(analysis));
        Mockito.when(mockSpaceRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(space));
        Mockito.when(mockuserRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockSpaceService.get(ArgumentMatchers.anyLong())).thenReturn(space);
        Mockito.when(mockUserService.loadUserByUsername(ArgumentMatchers.anyString())).thenReturn(user);
        Mockito.when(mockEventService.validInformation(user,idSpace,idSubject,idSubSubject)).thenReturn(true);
        Mockito.when(mockSubSubjectServiceImp.get(ArgumentMatchers.anyLong())).thenReturn(subSubject);
        Mockito.when(event.getNatureAction()).thenReturn(natureAction);
        Mockito.when(event.getNatureAction().getTitle()).thenReturn("Analyse");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/spaces/"+idSpace+"/subject/"+idSubject+"/subSubject/"+idSubSubject+"/event").param("isActive", String.valueOf(true)).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser
    public void testValidEvent() throws Exception {
        MockitoAnnotations.initMocks(this);
        Long idEvent= 1L;
        natureAction.setId(1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        event.setId(1L);
        event.setDate(date);
        event.setValidate(false);
        event.setNatureAction(natureAction);

        Analysis analyse = new Analysis(event);

        Task task = Mockito.mock(Task.class);
        task.setId(1L);
        task.setState(1);
        task.setEventValidate(event);

        Mockito.when(mockEventService.get(ArgumentMatchers.anyLong())).thenReturn(event);
        Mockito.when((event.getNatureAction())).thenReturn(natureAction);
        Mockito.when(natureAction.getTitle()).thenReturn("Analyse");
        Mockito.when(mockAnalyseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(analyse));
        Mockito.when(mockTaskRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(task));
        Mockito.when(task.getEventValidate()).thenReturn(event);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/user/event/"+idEvent+"/valid").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


    }




}
