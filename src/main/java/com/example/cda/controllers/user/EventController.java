package com.example.cda.controllers.user;

import com.example.cda.dtos.EventDto;
import com.example.cda.exceptions.*;
import com.example.cda.modeles.Consultation;
import com.example.cda.modeles.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.Principal;
import java.text.ParseException;

@RequestMapping(path = "/user")

public interface EventController {

    @PostMapping("spaces/{idSpace}/subject/{idSubject}/subSubject/{idSubSubject}/event")
    ResponseEntity<Event> save(@RequestBody EventDto dto, Principal principal, @PathVariable Long idSpace, @PathVariable Long idSubject,@PathVariable Long idSubSubject ) throws SubjectNotFoundException, URISyntaxException, SpaceNotFoundException, SubjectNotFoundException, NatureNotFoundException;

    @GetMapping("/event/{idEvent}")
    ResponseEntity<Event> get(Principal principal, @PathVariable Long idEvent );
    @GetMapping("/event")
    ResponseEntity<Iterable<Event>> getAllEventByUser( Principal principal, @RequestParam boolean isActive) ;

    @GetMapping("/spaces/{idSpace}/event")
    ResponseEntity<Iterable<Event>> getAllEventBySpace(@PathVariable Long idSpace, Principal principal, @RequestParam boolean isActive) throws SpaceNotFoundException;
    @GetMapping("spaces/{idSpace}/subject/{idSubject}/event")
    ResponseEntity<Iterable<Event>> getAllEventBySubject(@PathVariable Long idSpace, Principal principal, @PathVariable Long idSubject, @RequestParam boolean isActive) throws SpaceNotFoundException, SubjectNotFoundException;
    @GetMapping("spaces/{idSpace}/subject/{idSubject}/subSubject/{idSubSubject}/event")
    ResponseEntity<Iterable<Event>> getAllEventBySubSubject(@PathVariable Long idSpace, Principal principal, @PathVariable Long idSubject,  @PathVariable Long idSubSubject, @RequestParam boolean isActive) throws SpaceNotFoundException, SubjectNotFoundException;

    @PutMapping("/event/{idEvent}")
    public ResponseEntity<Event> update( @RequestBody EventDto dto, Principal principal, @PathVariable Long idEvent )throws ParseException;

    @PutMapping("/event/{idEvent}/valid")
    public ResponseEntity<Event> validEvent(@PathVariable Long idEvent);

    @DeleteMapping("spaces/{idSpace}/subject/{idSubject}/event/{idEvent}")
    public ResponseEntity<?> delete(@PathVariable Long idSpace,@PathVariable Long idSubject, Principal principal, @PathVariable Long idSubSubject, @PathVariable Long idEvent) throws URISyntaxException, SubjectNotFoundException, SubSubjectNotFoundException;


}
