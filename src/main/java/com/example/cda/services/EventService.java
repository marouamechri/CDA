package com.example.cda.services;

import com.example.cda.modeles.*;

import javax.xml.crypto.Data;
import java.util.List;

public interface EventService {

    Event save(Event event);
    Event get(Long id);

    List<Event> getAllEventBySubSubject(SubSubject subSubject, boolean isActive);
    List<Event> getListEventByDate(List<Event> events, String date );
    List<Event> getAllEventBySpace(Space space, boolean isActive);
    List<Event>getListEventByMonth(List<Event> events,int month, int year);
    List<Event> getListEventOrderByDate(List<Event> eventList, String order);
    List<Event> getListEventByNatureAction(List<Event> eventList, int idNatureAction, boolean isActive);

    void  delete (Event event);

    boolean validInformation(User user, Long idSpace, Long idSubject, Long idSubSubject);


    List<Event> getAllEventByUser(User user, boolean isActive);

    List<Event> getAllEventBySubject(Long idSubject, boolean isActive);

    Event getChidrenEvent(Event event);
    Event validationEvent(Event event);
    List<Consultation> getListConsultation(List<Event> events);
    List<Analysis>getListAnalyse(List<Event> events);
    List<Treatment> getListTreatment(List<Event> events);
}
