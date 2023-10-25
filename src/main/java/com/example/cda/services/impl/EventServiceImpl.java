package com.example.cda.services.impl;

import com.example.cda.modeles.*;
import com.example.cda.repositorys.EventRepository;
import com.example.cda.repositorys.NatureActionRepository;
import com.example.cda.repositorys.TaskRepository;
import com.example.cda.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public  class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    NatureActionRepository natureActionRepository;
    @Autowired
    SpaceService spaceService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    ConsultationService consultationService;
    @Autowired
    TreatmentService treatmentService;
    @Autowired
    AnalysisService analysisService;
    @Autowired
    TaskRepository taskRepository;

    @Override
    public Event save(Event event) {
      return   eventRepository.save(event);
    }

    @Override
    public Event get(Long idEvent) {
        return eventRepository.findById(idEvent).orElse(null);
    }

    @Override
    public List<Event> getAllEventBySubSubject(SubSubject subSubject, boolean isActive) {
        List<Event> events = subSubject.getEvents();
        List<Event> result = new ArrayList<>();
        if(events!=null){
            for (Event e: events) {
                if(e.isValidate()==isActive){
                    result.add(e);
                }

            }
        }
        return result;
    }

    @Override
    public List<Event> getListEventByDate(List<Event> events, String date) {
        List<Event> result = new ArrayList<>();

        for (Event e:events) {
            if(e.getDate().toString().startsWith(date)){
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public List<Event> getAllEventBySpace(Space space, boolean isActive) {
        List<Subject> subjects = space.getSubjects();
        List<Event> events = new ArrayList<>();
        if(subjects!=null){
            for (Subject s: subjects) {
                if(s.getSubSubjects()!=null){
                    for (SubSubject ss :s.getSubSubjects()) {
                        if(ss.getEvents()!=null){
                            for (Event e:ss.getEvents()) {
                               if (e.isValidate()==isActive){
                                   events.add(e);
                               }
                            }
                        }
                    }

                }
            }
        }
        return events;
    }

    @Override
    public List<Event> getListEventByMonth(List<Event> events, int month, int year) {

        List<Event> result = new ArrayList<>();
        for (Event e:events) {
            Calendar calendar = null;
            calendar =calendar.getInstance();
            calendar.setTime(e.getDate());
            int monthStart = calendar.get(Calendar.MONTH)+1;
            int yearsStart= calendar.get(Calendar.YEAR);

            System.out.println(monthStart);
            System.out.println(yearsStart);
            if(monthStart==month && yearsStart==year){
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public List<Event> getListEventOrderByDate(List<Event> eventList, String order) {
        List<Event> eventOrder = new ArrayList<>();
        if(order=="ASC"){
            eventOrder =  eventList.stream().sorted(Comparator.comparing(Event::getDate)).collect(Collectors.toList());
            return eventOrder;

        }else if(order=="DESC"){
            eventOrder =  eventList.stream().sorted(Comparator.comparing(Event::getDate, Comparator.reverseOrder())).collect(Collectors.toList());
            return eventOrder;
        }else{
            return eventList;
        }
    }

    @Override
    public List<Event> getListEventByNatureAction(List<Event> eventList, int idNatureAction, boolean isActive) {
        NatureAction natureAction = natureActionRepository.findById(idNatureAction).orElse(null);
        List<Event> result = new ArrayList<>();
        if(natureAction!=null){
            for (Event e:eventList) {
                if((e.getNatureAction().getId() == natureAction.getId())&& (e.isValidate() == isActive)){
                    result.add(e);
                }

            }
        }
        return result;

    }


    @Override
    public void delete(Event event) {
        eventRepository.delete(event);
    }

    @Override
    public boolean validInformation( User user,Long idSpace, Long idSubject, Long idSubSubject) {

        Space space= spaceService.get(idSpace);
        Subject subject = subjectService.get(idSubject);
        boolean result = false;
        if((space!=null) && (user.getId() == space.getUser().getId())
                &&(spaceService.subjectExistSpace(space, idSubject))&& subjectService.subSubjectExistSubject(subject,idSubSubject)){
                    result = true;
        }
        return result;
    }

    @Override
    public List<Event> getAllEventByUser(User user, boolean isActive) {
        List<Event> result = new ArrayList<>();
        List<Space> spaces = user.getSpaces();
        if(spaces!=null){
            for (Space s:spaces) {
                if(s.getSubjects()!=null){
                    for (Subject sub: s.getSubjects()){
                        if(sub.getSubSubjects()!=null){
                            for (SubSubject subSub: sub.getSubSubjects()){
                                if(subSub.getEvents()!=null){
                                    for (Event e:subSub.getEvents())
                                       if (e.isValidate() == isActive){
                                           result.add(e);

                                       }
                                }
                            }

                        }

                    }
                }

            }
        }

        return result;
    }

    @Override
    public List<Event> getAllEventBySubject(Long idSubject, boolean isActive) {
        List<Event> result = new ArrayList<>();
        Subject subject = subjectService.get(idSubject);
        if (subject!=null && subject.getSubSubjects()!=null){
            for (SubSubject s: subject.getSubSubjects()){
                if(s.getEvents()!=null){
                    for (Event e:s.getEvents()) {
                        if(e.isValidate()==isActive){
                            result.add(e);
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }

    @Override
    public Event getChidrenEvent(Event event) {
        NatureAction natureAction = event.getNatureAction();
        if(natureAction.getTitle().equals("Consultation")){

            return consultationService.get(event.getId());

        }else if(natureAction.getTitle().equals("Analyse")){

            return analysisService.get(event.getId());

        }else if(natureAction.getTitle().equals("Traitement")){

            return treatmentService.get(event.getId());
        }
        return null;
    }

    @Override
    public Event validationEvent(Event event) {
        boolean isValid= !event.isValidate();
        Task task = taskRepository.getByEventValidate(event);
        if(task!=null){
            try{
                if(isValid){
                    task.setState(2);
                }else {
                    task.setState(1);
                }
                taskRepository.save(task);
                event.setValidate(isValid);
                return eventRepository.save(event);
            }catch (Exception e){
                throw  e;
            }
        }
        event.setValidate(isValid);
        return eventRepository.save(event);

    }

    @Override
    public List<Consultation> getListConsultation(List<Event> events) {
        List<Consultation> consultations = new ArrayList<>() ;
        for (Event e:events) {
            if(e.getNatureAction().getTitle().equals("Consultation")){
                Consultation consultation = consultationService.get(e.getId());
                if(consultation!=null) {
                    consultations.add(consultation);
                }
            }

        }
        return consultations;
    }

    @Override
    public List<Analysis> getListAnalyse(List<Event> events) {
        List<Analysis> result = new ArrayList<>() ;
        for (Event e:events) {
            if(e.getNatureAction().getTitle().equals("Analyse")){
                Analysis analysis = analysisService.get(e.getId());
                if(analysis!=null) {
                    result.add(analysis);
                }
            }

        }
        return result;
    }

    @Override
    public List<Treatment> getListTreatment(List<Event> events) {
        List<Treatment> result = new ArrayList<>() ;
        for (Event e:events) {
            if(e.getNatureAction().getTitle().equals("Traitement")){
                Treatment treatment = treatmentService.get(e.getId());
                if(treatment!=null) {
                    result.add(treatment);
                }
            }

        }
        return result;
    }
}
