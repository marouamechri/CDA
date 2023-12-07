package com.example.cda.services;

import com.example.cda.dtos.ResponseEvent;
import com.example.cda.modeles.*;

import javax.xml.crypto.Data;
import java.util.List;

public interface EventService {

    Event save(Event event);
    void  delete (Event event);

    Event get(Long id);
    List<Event> getAllEventBySubSubject(SubSubject subSubject, boolean isValidate);
    List<Event> getListEventByDate(List<Event> events, String date );
    List<ResponseEvent> getAllEventBySpace(Space space, boolean isValidate);
    List<Event>getListEventByMonth(List<Event> events,int month, int year);
    List<Event> getListEventOrderByDate(List<Event> eventList, String order);
    List<Event> getListEventByNatureAction(List<Event> eventList, int idNatureAction, boolean isValidate);
    List<ResponseEvent> getAllEventByUser(User user, boolean isValidate);
    List<ResponseEvent> getAllEventBySubject(Long idSubject, boolean isActive);
    Event getChidrenEvent(Event event);
    List<Consultation> getListConsultation(List<Event> events);
    List<Analysis>getListAnalyse(List<Event> events);
    List<Treatment> getListTreatment(List<Event> events);


    //tester si les informations envoyer par le requête HTTP sont valides
    boolean validInformation(User user, Long idSpace, Long idSubject, Long idSubSubject);
    //valider un évènement
    Event validationEvent(Event event);
    //forcer la validation d'un évènement
    Event forceValidation (Event event);
}
