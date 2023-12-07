package com.example.cda.controllers.user.impl;

import com.example.cda.controllers.user.EventController;
import com.example.cda.dtos.EventDto;
import com.example.cda.dtos.ResponseEvent;
import com.example.cda.exceptions.*;
import com.example.cda.modeles.*;
import com.example.cda.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EventControllerImpl implements EventController {

    @Autowired
    EventService eventService;
    @Autowired
    NatureActionService natureActionService;
    @Autowired
    ConsultationService consultationService;
    @Autowired
    DoctorUserService doctorUserService;
    @Autowired
    MedicalSpecialtiesService medicalSpecialtiesService;
    @Autowired
    AnalysisService analysisService;
    @Autowired
    TreatmentService treatmentService;
    @Autowired
    SpaceService spaceService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    SubSubjectService subSubjectService;
    @Autowired
    UserService userService;
    @Autowired
    DocumentService documentService;
    @Override
    public ResponseEntity<Event> save(EventDto dto, Principal principal, Long idSpace, Long idSubject, Long idSubSubject) throws SubjectNotFoundException, URISyntaxException, SpaceNotFoundException, SubjectNotFoundException, NatureNotFoundException {


        User user = (User) userService.loadUserByUsername(principal.getName());
        NatureAction natureAction = natureActionService.get(dto.getNatureAction());
        SubSubject subSubject = subSubjectService.get(idSubSubject);
        if(eventService.validInformation(user, idSpace, idSubject, idSubSubject)){
            try {
                if(natureAction!=null && subSubject!=null){

                    String  nameNatureAction = natureAction.getTitle();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Event event = new Event(format.parse(dto.getDate()), false, subSubject, natureAction);

                    if(nameNatureAction.equals("Analyse")){
                        Analysis analysis = new Analysis(event);
                        Analysis result =  analysisService.save(analysis);
                        return ResponseEntity.status(201).body(result);
                    }else if(nameNatureAction.equals("Traitement")){
                        Treatment treatment = new Treatment(event, format.parse(dto.getDateFin()));
                        Treatment result = treatmentService.save(treatment);
                        return ResponseEntity.status(201).body(result);
                    }else if(nameNatureAction.equals("Consultation")){
                        DoctorUser doctor = doctorUserService.get(dto.getDoctor());
                        MedicalSpecialties speciality = medicalSpecialtiesService.get(dto.getMedicalSpecialties());
                        if(doctor !=null && speciality !=null){
                            Consultation consultation = new Consultation(event,doctor,speciality );
                            Consultation   result =  consultationService.save(consultation);
                            return ResponseEntity.status(201).body(result);
                        }else {
                            return  ResponseEntity.status(500).body(null);}
                    }

                }

            }catch (Exception e) {
                throw new NatureNotFoundException();
            }
        }
            return ResponseEntity.status(403).body(null);

    }

    @Override
    public ResponseEntity<ResponseEvent> get(Principal principal,@PathVariable Long idEvent) {

        Event event = eventService.get(idEvent);

       if(event!=null){
           String titleSubSubject = event.getSubSubject().getTitle();
           String titleSubject = event.getSubSubject().getSubject().getTitle();
           String nameSpace = event.getSubSubject().getSubject().getSpace().getName();

            Event eventChildren = eventService.getChidrenEvent(event);
            ResponseEvent result= new ResponseEvent(eventChildren,nameSpace, titleSubject,titleSubSubject  );

            return  ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(409).body(null);
    }

    @Override
    public ResponseEntity<List<ResponseEvent>> getAllEventByUser(Principal principal, boolean isValidate) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        List<ResponseEvent> events = eventService.getAllEventByUser(user,isValidate);

        return ResponseEntity.status(200).body(events);
    }

    @Override
    public ResponseEntity<List<ResponseEvent>> getAllEventBySpace(Long idSpace, Principal principal,boolean isValidate) throws SpaceNotFoundException {
        Space space =  spaceService.get(idSpace);
        if(space==null){
            throw new SpaceNotFoundException();
        }
        User user = (User)userService.loadUserByUsername(principal.getName());
        if(user!=null){
            if(user.getId()== space.getUser().getId() ){
                return ResponseEntity.status(200).body(eventService.getAllEventBySpace(space, isValidate));
            }
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<List<ResponseEvent>> getAllEventBySubject(Long idSpace, Principal principal, Long idSubject, @RequestParam boolean isValidate) throws SpaceNotFoundException, SubjectNotFoundException {
        Space space =  spaceService.get(idSpace);
        if(space==null){
            throw new SpaceNotFoundException();
        }
        User user =(User) userService.loadUserByUsername(principal.getName());
        if((user.getId() == space.getUser().getId())&&(spaceService.subjectExistSpace(space, idSubject)) ){

            return ResponseEntity.status(200).body(eventService.getAllEventBySubject(idSubject, isValidate));
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<List<ResponseEvent>> getAllEventBySubSubject(Long idSpace, Principal principal, Long idSubject, Long idSubSubject, @RequestParam boolean isValidate) throws SpaceNotFoundException, SubjectNotFoundException {

        User user = (User) userService.loadUserByUsername(principal.getName());
        if(eventService.validInformation(user, idSpace, idSubject, idSubSubject)){
            String nameSpace = spaceService.get(idSpace).getName();
            SubSubject subject= subSubjectService.get(idSubSubject);
            String titleSubject = subject.getTitle();
            String titleSubSubject = subSubjectService.get(idSubSubject).getTitle();
            Iterable<Event> events = eventService.getAllEventBySubSubject(subject, isValidate) ;
            List<ResponseEvent> result = new ArrayList<>();
            for (Event e:events) {
                ResponseEvent re = new ResponseEvent(e,nameSpace,titleSubject, titleSubSubject  );
                result.add(re);
            }
            return  ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(304).body(null);

    }

    @Override
    public ResponseEntity<Event> update( EventDto dto, Principal principal, Long idEvent) throws ParseException {

        Event event = eventService.get(idEvent);

        if(event!=null && eventService.getChidrenEvent(event)!=null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            if(event.getNatureAction().getTitle().equals("Analyse")) {
                Analysis analyse = (Analysis) eventService.getChidrenEvent(event) ;

                analyse.setDate(format.parse(dto.getDate()));
               Event result =  analysisService.save(analyse);
                return ResponseEntity.status(200).body(result);
            }else if(event.getNatureAction().getTitle().equals("Consultation")){
                Consultation consultation = (Consultation) eventService.getChidrenEvent(event);
                DoctorUser doctor = doctorUserService.get(dto.getDoctor());
                MedicalSpecialties medicalSpecialties = medicalSpecialtiesService.get(dto.getMedicalSpecialties());
                if(doctor!=null&& medicalSpecialties!=null) {
                    consultation.setDoctor(doctor);
                    consultation.setDate(format.parse(dto.getDate()));
                    consultation.setMedicalSpecialties(medicalSpecialties);
                    Consultation result = consultationService.save(consultation);
                    return ResponseEntity.status(200).body(result);
                }else{
                    return ResponseEntity.status(500).body(null);}
            } else if (event.getNatureAction().getTitle().equals("Traitement")) {
                Treatment treatment =(Treatment) eventService.getChidrenEvent(event);
                treatment.setDate(format.parse(dto.getDate()));
                treatment.setDateFin(format.parse((dto.getDateFin())));
                Treatment result = treatmentService.save(treatment);
                return ResponseEntity.status(200).body(result);
            }}
            return ResponseEntity.status(500).body(null);

    }

    @Override
    public ResponseEntity<Event> validEvent(Long idEvent) {
        Event event = eventService.get(idEvent);
        if(event!=null){
            return ResponseEntity.status(200).body(eventService.validationEvent(event));
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<?> delete(Long idSpace, Long idSubject, Principal principal, Long idSubSubject, Long idEvent) throws URISyntaxException, SubjectNotFoundException, SubSubjectNotFoundException {
        User user = (User) userService.loadUserByUsername(principal.getName());
        if(eventService.validInformation(user, idSpace, idSubject, idSubSubject)){

            Event event = eventService.get(idEvent);
            if(event!=null){
                eventService.delete(event);
                return ResponseEntity.status(200).body(null);

            }
            return ResponseEntity.status(500).body(null);

        }else
            return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<Event> forceValidEvent(Long idEvent) {
        Event event = eventService.get(idEvent);
        if(event==null){
            return  ResponseEntity.status(404).body(null);
        }else
            return  ResponseEntity.status(200).body(eventService.forceValidation(event));

    }



}

