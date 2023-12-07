package com.example.cda.controllers.user.impl;

import com.example.cda.controllers.user.TaskController;
import com.example.cda.dtos.EventDto;
import com.example.cda.dtos.TaskDto;
import com.example.cda.modeles.Event;
import com.example.cda.modeles.NatureAction;
import com.example.cda.modeles.Task;
import com.example.cda.modeles.User;
import com.example.cda.services.NatureActionService;
import com.example.cda.services.UserService;
import com.example.cda.services.impl.DoctorUserServiceImpl;
import com.example.cda.services.impl.EventServiceImpl;
import com.example.cda.services.impl.NatureActionServiceImpl;
import com.example.cda.services.impl.TaskServiceImpl;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.net.URISyntaxException;
import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TaskControllerImpl implements TaskController {
    @Autowired
    UserService userService;
    @Autowired
    TaskServiceImpl taskService;
    @Autowired
    NatureActionServiceImpl natureActionService;
    @Autowired
    EventServiceImpl eventService;


    @Override
    public ResponseEntity<Task> save(TaskDto dto, Principal principal, Long idSpace, Long idSubject, Long idSubSubject, Long idEvent) throws URISyntaxException {
        User user =(User) userService.loadUserByUsername(principal.getName());
        boolean valid = taskService.validInformation(user, idSpace, idSubject, idSubSubject, idEvent);
        if(valid){
            System.out.println("natureAction "+ dto.getIdNatureAction());

            NatureAction natureAction = natureActionService.get(dto.getIdNatureAction());

            Event event =  eventService.get(idEvent);

            if(natureAction==null|| event==null){
                return ResponseEntity.status(500).body(null);
            }else {

                Task task = new Task();
                task.setDescription(dto.getDescription());
                task.setEvent(event);
                task.setEventValidate(null);
                Date date= new Date();
                if(date.before(event.getDate())){
                    task.setTypeTask("avant");
                }else{
                    task.setTypeTask("apres");
                }
                task.setState(0);
                task.setNatureAction(natureAction);
                Task result = taskService.save(task);
                return ResponseEntity.status(201).body(result);
            }
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<Task> get(Principal principal, Long idSpace, Long idSubject, Long idSubSubject, Long idEvent, Long idTask) throws URISyntaxException {
        User user =(User) userService.loadUserByUsername(principal.getName());
        boolean valid = taskService.validInformation(user, idSpace, idSubject, idSubSubject, idEvent);
        if(valid){
            Task task = taskService.get(idTask);
            if(task!=null){
                return ResponseEntity.status(200).body(task);
            }else
                return ResponseEntity.status(500).body(null);
        }
        return ResponseEntity.status(304).body(null);
        }

    @Override
    public ResponseEntity<List<Task>> getAllTaskByEvent(Principal principal, Long idSpace, Long idSubject, Long idSubSubject, Long idEvent) throws URISyntaxException {
        User user =(User) userService.loadUserByUsername(principal.getName());
        boolean valid = taskService.validInformation(user, idSpace, idSubject, idSubSubject, idEvent);
        if(valid){
            Event event = eventService.get(idEvent);
            List<Task> result = taskService.getAllTaskByEvent(event);
            System.out.println(result);
            return ResponseEntity.status(200).body(result);
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<?> delete(Principal principal, Long idSpace, Long idSubject, Long idSubSubject, Long idEvent, Long idTask) throws URISyntaxException {
        User user =(User) userService.loadUserByUsername(principal.getName());
        boolean valid = taskService.validInformation(user, idSpace, idSubject, idSubSubject, idEvent);
        if(valid) {
            taskService.delete(idTask);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<Task> validTask(EventDto dto, Principal principal, Long idSpace, Long idSubject, Long idSubSubject, Long idEvent, Long idTask) throws URISyntaxException, ParseException {
        User user =(User) userService.loadUserByUsername(principal.getName());
        boolean valid = taskService.validInformation(user, idSpace, idSubject, idSubSubject, idEvent);
        if(valid) {
            Task task = taskService.get(idTask);
            if(task!=null){
                return    ResponseEntity.status(200).body(taskService.validTask(task, dto));
            }
            return ResponseEntity.status(500).body(null);
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<Task> update(TaskDto dto, Principal principal, Long idSpace, Long idSubject, Long idSubSubject, Long idEvent, Long idTask) throws URISyntaxException {
        User user =(User) userService.loadUserByUsername(principal.getName());
        boolean valid = taskService.validInformation(user, idSpace, idSubject, idSubSubject, idEvent);
        if(valid){
            Task task = taskService.get(idTask);
            if(task!=null){
                NatureAction natureAction = natureActionService.get(dto.getIdNatureAction());
                if(natureAction!=null){
                    task.setNatureAction(natureAction);
                    task.setDescription(dto.getDescription());
                    Task result = taskService.save(task);
                    return ResponseEntity.status(200).body(result);
                }
                else  return ResponseEntity.status(500).body(null);

            }else
                return ResponseEntity.status(500).body(null);
        }
        return ResponseEntity.status(304).body(null);
    }

    @Override
    public ResponseEntity<Task> forceValid(TaskDto dto, Principal principal, Long idSpace, Long idSubject, Long idSubSubject, Long idEvent, Long idTask) throws URISyntaxException {
        User user =(User) userService.loadUserByUsername(principal.getName());
        boolean valid = taskService.validInformation(user, idSpace, idSubject, idSubSubject, idEvent);
        if(valid){
            Task task = taskService.get(idTask);
            if(task!=null){
                if(task.getState()==0){
                    task.setState(2);
                }else {
                    task.setState(0);
                }
                Task result  = taskService.save(task);
                return ResponseEntity.status(200).body(result);
            }else
                return ResponseEntity.status(500).body(null);
        }
        return ResponseEntity.status(304).body(null);
    }

}
