package com.example.cda.controllers.user;

import com.example.cda.dtos.EventDto;
import com.example.cda.dtos.TaskDto;
import com.example.cda.modeles.Task;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@RequestMapping(path = "/user/spaces/{idSpace}/subject/{idSubject}/subSubject/{idSubSubject}/event/{idEvent}")
public interface TaskController {

    @PostMapping("/task")
    ResponseEntity<Task> save(@RequestBody TaskDto dto, Principal principal, @PathVariable Long idSpace, @PathVariable Long idSubject, @PathVariable Long idSubSubject , @PathVariable Long idEvent) throws URISyntaxException;
    @GetMapping("/task/{idTask}")
    ResponseEntity<Task> get( Principal principal, @PathVariable Long idSpace, @PathVariable Long idSubject, @PathVariable Long idSubSubject , @PathVariable Long idEvent, @PathVariable Long idTask) throws URISyntaxException;

    @GetMapping("/task")
    ResponseEntity<List<Task>> getAllTaskByEvent(Principal principal, @PathVariable Long idSpace, @PathVariable Long idSubject, @PathVariable Long idSubSubject , @PathVariable Long idEvent) throws URISyntaxException;
    @DeleteMapping("/task")
    ResponseEntity<?> delete( Principal principal, @PathVariable Long idSpace, @PathVariable Long idSubject, @PathVariable Long idSubSubject , @PathVariable Long idEvent, @PathVariable Long idTask) throws URISyntaxException;
    @PutMapping("/task/{idTask}/valid")
    ResponseEntity<Task> validTask(@RequestBody EventDto dto, Principal principal, @PathVariable Long idSpace, @PathVariable Long idSubject, @PathVariable Long idSubSubject , @PathVariable Long idEvent, @PathVariable Long idTask) throws URISyntaxException, ParseException;


}
