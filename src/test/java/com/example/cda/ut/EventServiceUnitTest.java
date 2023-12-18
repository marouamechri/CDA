package com.example.cda.ut;

import com.example.cda.dtos.ResponseEvent;
import com.example.cda.modeles.*;
import com.example.cda.repositorys.EventRepository;
import com.example.cda.repositorys.NatureActionRepository;
import com.example.cda.repositorys.TaskRepository;
import com.example.cda.services.impl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventServiceUnitTest {
    @Autowired
    EventServiceImpl eventService;
    @MockBean
    EventRepository mockeventRepository;
    @MockBean
    TaskRepository mockTaskRepository;
    @MockBean
    NatureActionRepository mockNatureActionRepository;
    @MockBean
    ConsultationServiceImpl mockConsultationService;
    @MockBean
    AnalysisServiceImpl mockAnalysisService;
    @MockBean
    TreatmentServiceIpm mockTreatmentService;
    @Mock
    SubSubject subSubject1;
    @Mock
    SubSubject subSubject2;
    @Mock
    Space space;
    @Mock
    User user;
    @Mock
    Subject subject1;
    @Mock
    Subject subject2;
    @MockBean
    SpaceServiceImpl mockSpaceService;
    @MockBean
    SubjectServiceImpl mockSubjectService;
    @Mock
    NatureAction natureAction;
    @Mock
    Event event;
    @Mock
    Task task;
    @Test
    @WithMockUser()
    public void testSaveEvent() throws ParseException {

        //simulé event retour repository
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        Event event = new Event();
        event.setId(1L);
        event.setDate(date);
        //simulé event service
        Date date2 = format.parse("2022-05-15 11:15");
        Event event2 = new Event();
        event.setId(2L);
        event.setDate(date);

        Mockito.when(mockeventRepository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);

        //Assertions
        Event result = eventService.save(event2);
        Assertions.assertEquals("2023-05-15 11:15",format.format(result.getDate()));

    }

    @Test
    @WithMockUser()
    public void testGetEvent() throws ParseException {
        //simulé event retour repository
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        Event event = new Event();
        event.setId(1L);
        event.setDate(date);

        //simulé event service
        Date date2 = format.parse("2022-05-15 11:15");
        Event event2 = new Event();
        event.setId(2L);
        event.setDate(date);

        Mockito.when(mockeventRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(event));
        //Assertions
        Event result = eventService.get(ArgumentMatchers.anyLong());
        assert result!=null;
        Assertions.assertEquals("2023-05-15 11:15",format.format(result.getDate()));

    }
    @Test
    @WithMockUser()
    public void testGetAllEventBySubSubject(){
        //preparer les test
        Event event1 = new Event();
        event1.setValidate(true);

        Event event2 = new Event();
        event2.setValidate(true);

        Event event3 = new Event();
        event3.setValidate(false);

        List<Event>events =  new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        //simulé le comportement de la méthode subSubject.getEvents()
        Mockito.when(subSubject1.getEvents()).thenReturn(events);

        //appelé la methode à tester
        List<Event> result = eventService.getAllEventBySubSubject(subSubject1,true);

        //verifié les resultats
        Assertions.assertEquals(2, result.size());

    }
  /*  @Test
    @WithMockUser()
    public void getListEventByDateTest() throws ParseException {
        //preparer les test
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = format.parse("2023-05-15 11:15");
        Date date2 = format.parse("2023-05-10 11:15");
        Date date3 = format.parse("2021-05-15 11:15");


        Event event1 = new Event();
        event1.setDate(date1);

        Event event2 = new Event();
        event2.setDate(date2);

        Event event3 = new Event();
        event3.setDate(date3);

        List<Event>events =  new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);

        String date="2023-05-15";

        //appelé la methode à tester
        List<Event> result = eventService.getListEventByDate(events, date);

        //verifié les resultats
        Assertions.assertEquals(1, result.size());
    }*/

    @Test
    @WithMockUser()
    public  void testGetAllEventBySpace(){

        //preparer les test
        //créé les space
        space.setId(1L);

        // créé le subject
        subject1.setId(1L);
        //creé des sous-subject

        subSubject1.setId(1L);
        subSubject2.setId(2L);

        Event event1 = new Event();
        event1.setValidate(true);
        event1.setSubSubject(subSubject1);

        Event event2 = new Event();
        event2.setValidate(true);
        event2.setSubSubject(subSubject1);

        Event event3 = new Event();
        event3.setValidate(false);
        event3.setSubSubject(subSubject2);


       Mockito.when(space.getSubjects()).thenReturn(List.of(subject1));
       Mockito.when(subject1.getSubSubjects()).thenReturn(List.of(subSubject1, subSubject2)) ;
       Mockito.when(subSubject1.getEvents()) .thenReturn(List.of(event2, event1));
       Mockito.when(subSubject2.getEvents()).thenReturn(List.of(event3))   ;

        //Appeler la methode à tester
        List<ResponseEvent> result = eventService.getAllEventBySpace(space,true);

        //Vérifier les résultat
        Assertions.assertEquals(2, result.size());

    }

    @Test
    @WithMockUser()
    public void testGetListEventByMonth() throws ParseException {
        //preparer les test
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = format.parse("2023-05-15 11:15");
        Date date2 = format.parse("2023-06-10 11:15");
        Date date3 = format.parse("2021-05-15 11:15");


        Event event1 = new Event();
        event1.setDate(date1);

        Event event2 = new Event();
        event2.setDate(date2);

        Event event3 = new Event();
        event3.setDate(date3);

        List<Event>events =  new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);

        //appeler la methode à tester pour le mois de mai
        List<Event> result = eventService.getListEventByMonth(events,5,2023);

        //verifier les résultats
        Assertions.assertEquals(1, result.size());

    }
    @Test
    @WithMockUser()
    public void testGetListEventOrderByDateAsc() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = format.parse("2023-05-15 11:15");
        Date date2 = format.parse("2023-05-10 11:20");
        Date date3 = format.parse("2021-05-15 11:15");


        Event event1 = new Event();
        event1.setDate(date1);

        Event event2 = new Event();
        event2.setDate(date2);

        Event event3 = new Event();
        event3.setDate(date3);

        List<Event>events =  new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);

        //appeler la methode à tester pour le mois de mai
        List<Event> result = eventService.getListEventOrderByDate(events,"ASC");

        List<Event> expectedAscOrder = Arrays.asList(event3, event2,event1);
        Assertions.assertEquals(expectedAscOrder, result);

    }
    @Test
    @WithMockUser()
    public void testGetListEventOrderByDateDesc() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = format.parse("2023-05-15 11:15");
        Date date2 = format.parse("2023-05-10 11:20");
        Date date3 = format.parse("2021-05-15 11:15");


        Event event1 = new Event();
        event1.setDate(date1);

        Event event2 = new Event();
        event2.setDate(date2);

        Event event3 = new Event();
        event3.setDate(date3);

        List<Event>events =  new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);

        //appeler la methode à tester pour le mois de mai
        List<Event> result = eventService.getListEventOrderByDate(events,"DESC");

        List<Event> expectedAscOrder = Arrays.asList(event1, event2,event3);
        Assertions.assertEquals(expectedAscOrder, result);

    }
    @Test
    @WithMockUser()
    public void testGetListEventByNatureAction(){
        //crée un objet NatureAction de test
        NatureAction natureAction1 = new NatureAction();
        natureAction1.setId(1);
        natureAction1.setTitle("Analyse");

        NatureAction natureAction2 = new NatureAction();
        natureAction2.setId(2);
        natureAction2.setTitle("Consultation");

        Event event1 = new Event();
        event1.setId(1L);
        event1.setValidate(false);
        event1.setNatureAction(natureAction1);

        Event event2 = new Event();
        event2.setId(2L);
        event2.setValidate(false);
        event2.setNatureAction(natureAction1);

        Event event3 = new Event();
        event3.setId(3L);
        event3.setValidate(false);
        event3.setNatureAction(natureAction2);

        List<Event>events =  new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        //simulé le comportement de natureActionRepository.findById
        Mockito.when(mockNatureActionRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(natureAction1));
        //appelé la methode à tester
        List<Event> result = eventService.getListEventByNatureAction(events,1, false);

        //Vérifié les resultats
        Assertions.assertEquals(2, result.size());
    }
    @Test
    @WithMockUser()
    public void testValidInformation(){

        long idSpace = 1L;
        long idSubject =2L;
        long idSubSubject = 3L;
        int userId = 4;

        User user = new User();
        user.setId(userId);

        Space space1 = new Space();
        space1.setId(idSpace);
        space1.setUser(user);

        Subject subject = new Subject();
        subject.setId(idSubSubject);

        //simuler le comportement des service SpaceService et SubjectService
        Mockito.when(mockSpaceService.get(idSpace)).thenReturn(space1);
        Mockito.when(mockSubjectService.get(idSubject)).thenReturn(subject);
        Mockito.when(mockSpaceService.subjectExistSpace(space1, idSubject)).thenReturn(true);
        Mockito.when(mockSubjectService.subSubjectExistSubject(subject,idSubSubject)).thenReturn(true);

        //Appeler la methode à tester
        boolean result = eventService.validInformation(user,idSpace, idSubject, idSubSubject);

        //Verifier les résultats
        Assertions.assertTrue(result);


    }
    @Test
    @WithMockUser
    public void testGetAllEventByUser(){

        //preparer les test
        //cree l'user
        user.setId(1);
        //créé les space
        space.setId(1L);

        // créé le subject
        subject1.setId(1L);
        subject2.setId(2L);
        //creé des sous-subject

        subSubject1.setId(1L);
        subSubject2.setId(2L);

        Event event1 = new Event();
        event1.setId(1L);
        event1.setValidate(true);

        Event event2 = new Event();
        event2.setId(2L);
        event2.setValidate(true);

        Event event3 = new Event();
        event3.setId(3L);
        event3.setValidate(false);

        Mockito.when(user.getSpaces()).thenReturn(List.of(space));
        Mockito.when((space.getSubjects())).thenReturn(List.of(subject1))  ;
        Mockito.when(subject1.getSubSubjects()) .thenReturn(List.of(subSubject1,subSubject2));
        Mockito.when((subSubject1.getEvents())).thenReturn(List.of(event1, event2)) ;
        Mockito.when(subSubject2.getEvents())  .thenReturn(List.of(event3))    ;

        //Appeler la methode à tester
        List<ResponseEvent> result = eventService.getAllEventByUser(user,true);

        //Vérifier les résultat
        Assertions.assertEquals(2, result.size());

    }
    @Test
    @WithMockUser
    public void testGetAllEventBySubject(){

        //preparer les test

        long idSubject  = 1L;
        // preparer le subject
        subject1.setId(1L);

        //preparer des sous-subject
        subSubject1.setId(1L);
        subSubject2.setId(2L);
        subSubject2.setSubject(subject1);

        //créer les events
        Event event1 = new Event();
        event1.setId(1L);
        event1.setValidate(true);

        Event event2 = new Event();
        event2.setId(2L);
        event2.setValidate(true);

        Event event3 = new Event();
        event3.setId(3L);
        event3.setValidate(false);

        Mockito.when(mockSubjectService.get(idSubject)).thenReturn(subject1);
        Mockito.when(subject1.getSubSubjects()).thenReturn(List.of(subSubject1, subSubject2));
        Mockito.when(subSubject1.getEvents()) .thenReturn(List.of(event1, event2));
        Mockito.when((subSubject2.getEvents())).thenReturn(List.of(event3)) ;


        //Appeler la methode à tester
        List<ResponseEvent> result = eventService.getAllEventBySubject(idSubject,true);

        //Vérifier les résultat
        Assertions.assertEquals(2, result.size());


    }
    @Test
    @WithMockUser
    public void testGetChildrenConsultationEvent() throws ParseException {

        MockitoAnnotations.initMocks(this);

        natureAction.setId(1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        event.setId(1L);
        event.setValidate(false);
        event.setNatureAction(natureAction);
        event.setDate(date);

        //simuler le comportement du service
        Mockito.when(natureAction.getTitle()) .thenReturn("Consultation") ;
        Mockito.when(event.getNatureAction()) .thenReturn(natureAction) ;
        Mockito.when(event.getNatureAction().getTitle()).thenReturn("Consultation");
        //créer un objet consultation
        Consultation consultation = Mockito.mock(Consultation.class);
        consultation.setId(1L);
        consultation.setDate(event.getDate());
        //simuler le comportement consultationService.get
       Mockito.when(mockConsultationService.get(1L)).thenReturn(consultation) ;

       //appler la méthode du test
       Event result = eventService.getChidrenEvent(event);
       //assert result!=null;
       //vérifier la resultat
       Assertions.assertEquals("2023-05-15 11:15", format.format(result.getDate()));


    }
    @Test
    @WithMockUser
    public void testGetChildrenAnalysisEvent() throws ParseException {
        MockitoAnnotations.initMocks(this);

        natureAction.setId(1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        event.setId(1L);
        event.setValidate(false);
        event.setNatureAction(natureAction);
        event.setDate(date);
        DoctorUser doctor = new DoctorUser();
        doctor.setId(1L);
        MedicalSpecialties medicalSpecialties = new MedicalSpecialties();
        medicalSpecialties.setId(1);

        //simuler le comportement du service
        //Mockito.when(eventService.getChidrenEvent(event)).thenCallRealMethod();
        Mockito.when(natureAction.getTitle()) .thenReturn("Analyse") ;
        Mockito.when(event.getNatureAction()) .thenReturn(natureAction) ;
        Mockito.when(event.getNatureAction().getTitle()).thenReturn("Analyse");
        //créer un objet analyse
        Analysis analysis = new Analysis(event);
        analysis.setId(1L);
        //simuler le comportement consultationService.get
        Mockito.when(mockAnalysisService.get(event.getId())) .thenReturn(analysis) ;

        //appler la méthode du test
        Event result = eventService.getChidrenEvent(event);
        //assert result!=null;
        //vérifier la resultat
        Assertions.assertEquals("2023-05-15 11:15", format.format(result.getDate()));
    }
    @Test
    @WithMockUser
    public void testGetChildrenTrainmenEvent() throws ParseException {
        MockitoAnnotations.initMocks(this);

        NatureAction natureAction = Mockito.mock(NatureAction.class);
        natureAction.setId(1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2023-05-15 11:15");
        Event event = Mockito.mock(Event.class);
        event.setId(1L);
        event.setValidate(false);
        event.setNatureAction(natureAction);
        event.setDate(date);

        //simuler le comportement
        Mockito.when(event.getNatureAction()) .thenReturn(natureAction) ;
        Mockito.when(event.getNatureAction().getTitle()).thenReturn("Traitement");
        Mockito.when(natureAction.getTitle()) .thenReturn("Traitement") ;
        //créer un objet Treatment
        Treatment treatment = Mockito.mock(Treatment.class);
        treatment.setId(1L);
        treatment.setDate(event.getDate());
        //simuler le comportement consultationService.get
        Mockito.when(mockTreatmentService.get(event.getId())) .thenReturn(treatment) ;

        //appler la méthode du test
        Event result = eventService.getChidrenEvent(event);
        //assert result!=null;
        //vérifier la resultat
        Assertions.assertEquals("2023-05-15 11:15", format.format(result.getDate()));
    }
    @Test
    @WithMockUser
    public void testValidationEvent(){

        MockitoAnnotations.initMocks(this);

        event.setId(1L);
        //créer un objet Task de test
        task.setId(1L);
        task.setState(1);

        Mockito.when(event.isValidate()).thenReturn(false);

        //simuler le comportement de TaskRepository.getByEventValidate(event)
        Mockito.when(mockTaskRepository.getByEventValidate(ArgumentMatchers.any(Event.class))).thenReturn(task);

        Mockito.when(event.isValidate()).thenReturn(false);

        //Appler la méthode à tester
        Event  result = eventService.validationEvent(event);

        //verifier les résultats
        Assertions.assertEquals(true,result.isValidate());
        Assertions.assertEquals(2, task.getState());



    }
    @Test
    @WithMockUser
    public void testGetListConsultation(){

        MockitoAnnotations.initMocks(this);

        //preparer les objet event et task
        natureAction.setId(1);
        natureAction.setTitle("Consultation");

        NatureAction natureAction2 = Mockito.mock(NatureAction.class);
        natureAction2.setId(2);
        natureAction2.setTitle("Analyse");

        Event event1 = Mockito.mock(Event.class);
        event1.setId(1L);
        event1.setValidate(true);
        event1.setNatureAction(natureAction);

        Event event2 = Mockito.mock(Event.class);
        event2.setId(2L);
        event2.setValidate(true);
        event2.setNatureAction(natureAction2);

        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        //créer l'objet Consultation
        Consultation consultation =Mockito.mock(Consultation.class);
        consultation.setId(1l);
        //Simuler le comportement de consultationService.get()
        Mockito.when(mockConsultationService.get(1L)).thenReturn(consultation);
        Mockito.when(natureAction.getTitle()).thenReturn("Consultation");
        Mockito.when((natureAction2.getTitle())).thenReturn("Analyse");
        Mockito.when(event1.getNatureAction()).thenReturn(natureAction);
        Mockito.when(event2.getNatureAction()).thenReturn(natureAction2);
        Mockito.when(eventService.getChidrenEvent(event1)).thenReturn(consultation);

        //Appler la methode à tester
        List<Consultation> result = eventService.getListConsultation(events);
        //verifier les résultats
        Assertions.assertEquals(1, result.size());

    }

    @Test
    @WithMockUser
    public void testGetListAnalysis(){

        MockitoAnnotations.initMocks(this);

        //preparer les objet event et task
        natureAction.setId(1);

        NatureAction natureAction2 = Mockito.mock(NatureAction.class);
        natureAction2.setId(2);

        Event event1 = Mockito.mock(Event.class);
        event1.setId(1L);

        Event event2 = Mockito.mock(Event.class);
        event2.setId(2L);

        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        //créer l'objet Analyse
        Analysis analysis = Mockito.mock(Analysis.class);
        analysis.setId(1l);
        //Simuler le comportement de consultationService.get()
        Mockito.when(mockAnalysisService.get(1L)).thenReturn(analysis);
        Mockito.when(natureAction.getTitle()).thenReturn("Analyse");
        Mockito.when((natureAction2.getTitle())).thenReturn("Consultation");
        Mockito.when(event1.getNatureAction()).thenReturn(natureAction);
        Mockito.when(event2.getNatureAction()).thenReturn(natureAction2);
        Mockito.when(eventService.getChidrenEvent(event1)).thenReturn(analysis);

        //Appler la methode à tester
        List<Analysis> result = eventService.getListAnalyse(events);
        //verifier les résultats
        Assertions.assertEquals(1, result.size());

    }
    @Test
    @WithMockUser
    public void testGetListTreatment(){

        MockitoAnnotations.initMocks(this);

        //preparer les objet event et task
        natureAction.setId(1);

        NatureAction natureAction2 = Mockito.mock(NatureAction.class);
        natureAction2.setId(2);

        Event event1 = Mockito.mock(Event.class);
        event1.setId(1L);

        Event event2 = Mockito.mock(Event.class);
        event2.setId(2L);

        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        //créer l'objet Analyse
        Treatment treatment = Mockito.mock(Treatment.class);
        treatment.setId(1l);
        //Simuler le comportement de consultationService.get()
        Mockito.when(mockTreatmentService.get(1L)).thenReturn(treatment);
        Mockito.when(natureAction.getTitle()).thenReturn("Traitement");
        Mockito.when((natureAction2.getTitle())).thenReturn("Consultation");
        Mockito.when(event1.getNatureAction()).thenReturn(natureAction);
        Mockito.when(event2.getNatureAction()).thenReturn(natureAction2);
        Mockito.when(eventService.getChidrenEvent(event1)).thenReturn(treatment);

        //Appler la methode à tester
        List<Treatment> result = eventService.getListTreatment(events);
        //verifier les résultats
        Assertions.assertEquals(1, result.size());

    }




}
