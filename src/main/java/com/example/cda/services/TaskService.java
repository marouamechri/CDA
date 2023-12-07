package com.example.cda.services;

import com.example.cda.dtos.EventDto;
import com.example.cda.modeles.Document;
import com.example.cda.modeles.Event;
import com.example.cda.modeles.Task;
import com.example.cda.modeles.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.ParseException;
import java.util.List;

public interface TaskService {

    Task save(Task task);
    Task get(Long idTask);
    List<Task> getAllTaskByEvent(Event event);
    void delete(Long idTask);
    Task validTask(Task task, EventDto dto) throws ParseException;
    boolean validInformation(User user, Long idSpace, Long idSubject, Long idSubSubject , Long idEvent);


}
