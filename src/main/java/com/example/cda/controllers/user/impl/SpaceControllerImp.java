package com.example.cda.controllers.user.impl;

import com.example.cda.controllers.user.SpaceController;
import com.example.cda.dtos.SpaceDto;
import com.example.cda.exceptions.SpaceExistException;
import com.example.cda.exceptions.SpaceNotFoundException;
import com.example.cda.exceptions.UserNotFoundException;
import com.example.cda.modeles.Space;
import com.example.cda.modeles.User;
import com.example.cda.services.JwtUserService;
import com.example.cda.services.impl.SpaceServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@RestController
public class SpaceControllerImp  implements SpaceController {
    @Autowired
    SpaceServiceImpl spaceService;
    @Autowired
    JwtUserService userService;

    @Override
    public ResponseEntity<Space> save(@Valid @RequestBody SpaceDto dto, Principal  principal) throws SpaceExistException, URISyntaxException, UserNotFoundException {
       //System.out.println("debut");
        Space newSpace = new Space();
        newSpace.setName(dto.getName());
        UserDetails user = userService.loadUserByUsername(principal.getName());
        System.out.println("user"+user);
        if(user!=null){
            newSpace.setUser((User)user);
            Space result =  spaceService.save(newSpace,(User) user);
            if(result==null){
                System.out.println("result null");
                throw new SpaceExistException();
            }
            return ResponseEntity.status(201).body(result);
        }else
            throw new UserNotFoundException();

    }
    @Override
    public ResponseEntity<Space> get(@PathVariable Long id, Principal principal) throws URISyntaxException, SpaceNotFoundException {
        Space result =  spaceService.get(id);
        if(result==null){
            throw new SpaceNotFoundException();
        }
        User user = (User)userService.loadUserByUsername(principal.getName());
        if(user!=null){
            if(user.getId()== result.getUser().getId() ){
                return ResponseEntity.status(200).body(result);
            }else
                return ResponseEntity.status(403).body(null);
        }else
            return ResponseEntity.status(403).body(null);

    }
    @Override
    public  ResponseEntity<List<Space>> getAllSpaceByUser(Principal principal)  {
        UserDetails user = userService.loadUserByUsername(principal.getName());

        if(user!=null){
            return ResponseEntity.status(200).body(spaceService.getAllSpaceByUser(user));
        }else {
            return ResponseEntity.status(403).body(null);
        }
    }
    @Override
    public ResponseEntity<Space> update(@PathVariable Long id,@Valid @RequestBody SpaceDto dto, Principal principal ) throws URISyntaxException, SpaceNotFoundException {

        Space spaceExist = spaceService.get(id);
        if(spaceExist==null){
            throw new SpaceNotFoundException();
        }
        User user =(User) userService.loadUserByUsername(principal.getName());
        if(user!=null){
            if(user.getId() == spaceExist.getUser().getId() ){
                Space result = spaceService.update(spaceExist, dto.getName());
                return ResponseEntity.status(200).body(result);
            }
             return ResponseEntity.status(403).body(null);
        }

        return ResponseEntity.status(403).body(null);

    }

    @Override
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) throws URISyntaxException, SpaceNotFoundException {
        User user = (User) userService.loadUserByUsername(principal.getName());
        Space spaceExist = spaceService.get(id);
        if(user!=null){
            if (user.getId()== spaceExist.getUser().getId() ) {
                return ResponseEntity.status(403).body(null);
            }
        }
        if (spaceExist == null) {
            throw new SpaceNotFoundException();
        }
        /*try{
            List<Event> eventList = spaceExist.getEvents();
            for (Event event:eventList) {
               for (Task t:event.getTasks()) {
                    taskService.delete(t);
                }
                eventService.delete(event);
            }*/
        spaceService.delete(id);
        return ResponseEntity.noContent().build();// Sending a 204 HTTP status code

       /* }catch (Exception e){
            throw e;
        }*/
    }

}
