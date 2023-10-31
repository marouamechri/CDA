package com.example.cda.controllers.user.impl;

import com.example.cda.controllers.user.EventController;
import com.example.cda.dtos.EventDto;
import com.example.cda.exceptions.*;
import com.example.cda.modeles.*;
import com.example.cda.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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

    @Override
    public ResponseEntity<Event> save(EventDto dto, Principal principal, Long idSpace, Long idSubject, Long idSubSubject) throws SubjectNotFoundException, URISyntaxException, SpaceNotFoundException, SubjectNotFoundException, NatureNotFoundException {

        User user = (User) userService.loadUserByUsername(principal.getName());
        NatureAction natureAction = natureActionService.get(dto.getNatureAction());
        SubSubject subSubject = subSubjectService.get(idSubSubject);
        if(eventService.validInformation(user, idSpace, idSubject, idSubSubject)){
            try {
                if(natureAction!=null && subSubject!=null){

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Event event = new Event(format.parse(dto.getDate()), false, subSubject, natureAction);

                    if(natureAction.getTitle().equals("Consultation")){
                        DoctorUser doctor = doctorUserService.get(dto.getDoctor());
                        MedicalSpecialties speciality = medicalSpecialtiesService.get(dto.getMedicalSpecialties());
                        if(doctor !=null && speciality !=null){
                            Consultation consultation = new Consultation(event,doctor,speciality );
                            Consultation   result =  consultationService.save(consultation);
                            return ResponseEntity.status(201).body(result);
                        }else {
                            return  ResponseEntity.status(500).body(null);}
                    } else if(natureAction.getTitle().equals("Analyse")){

                        Analysis analysis = new Analysis(event);

                       Analysis result =  analysisService.save(analysis);
                       return ResponseEntity.status(201).body(result);

                    }else if(natureAction.getTitle().equals("traitement")){
                        Treatment treatment = new Treatment(event, format.parse(dto.getDateFin()));
                        Treatment result = treatmentService.save(treatment);
                        return ResponseEntity.status(201).body(result);
                    }
                }

            }catch (Exception e) {
                throw new NatureNotFoundException();
            }
        }
            return ResponseEntity.status(403).body(null);

    }

    @Override
    public ResponseEntity<Event> get(Principal principal,@PathVariable Long idEvent) {

        Event event = eventService.get(idEvent);

       if(event!=null){

            Event result = eventService.getChidrenEvent(event);
            return  ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(409).body(null);
    }

    @Override
    public ResponseEntity<Iterable<Event>> getAllEventByUser(Principal principal, boolean isActive) {
        User user = (User) userService.loadUserByUsername(principal.getName());

        return ResponseEntity.status(200).body(eventService.getAllEventByUser(user, isActive));
    }

    @Override
    public ResponseEntity<Iterable<Event>> getAllEventBySpace(Long idSpace, Principal principal,boolean isActive) throws SpaceNotFoundException {
        Space space =  spaceService.get(idSpace);
        if(space==null){
            throw new SpaceNotFoundException();
        }
        User user = (User)userService.loadUserByUsername(principal.getName());
        if(user!=null){
            if(user.getId()== space.getUser().getId() ){
                return ResponseEntity.status(200).body(eventService.getAllEventBySpace(space, isActive));
            }
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<Iterable<Event>> getAllEventBySubject(Long idSpace, Principal principal, Long idSubject, @RequestParam boolean isActive) throws SpaceNotFoundException, SubjectNotFoundException {
        Space space =  spaceService.get(idSpace);
        if(space==null){
            throw new SpaceNotFoundException();
        }
        User user =(User) userService.loadUserByUsername(principal.getName());
        if((user.getId() == space.getUser().getId())&&(spaceService.subjectExistSpace(space, idSubject)) ){

            return ResponseEntity.status(200).body(eventService.getAllEventBySubject(idSubject, isActive));
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<Iterable<Event>> getAllEventBySubSubject(Long idSpace, Principal principal, Long idSubject, Long idSubSubject, @RequestParam boolean isActive) throws SpaceNotFoundException, SubjectNotFoundException {

        User user = (User) userService.loadUserByUsername(principal.getName());
        if(eventService.validInformation(user, idSpace, idSubject, idSubSubject)){
            SubSubject subject= subSubjectService.get(idSubSubject);
            return  ResponseEntity.status(200).body(eventService.getAllEventBySubSubject(subject, isActive));
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
                if(doctor!=null&& medicalSpecialties!=null){
                    consultation.setDoctor(doctor);
                    consultation.setDate(format.parse(dto.getDate()));
                    consultation.setMedicalSpecialties(medicalSpecialties);
                    Consultation result = consultationService.save(consultation);
                    return ResponseEntity.status(200).body(result);
                }
                return ResponseEntity.status(500).body(null);
            } else if (event.getNatureAction().equals("traitement")) {
                Treatment treatment =(Treatment) eventService.getChidrenEvent(event);
                treatment.setDate(format.parse(dto.getDate()));
                Treatment result = treatmentService.save(treatment);
                return ResponseEntity.status(200).body(result);
            }
            return ResponseEntity.status(500).body(null);

        }
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
        return null;
    }
}
