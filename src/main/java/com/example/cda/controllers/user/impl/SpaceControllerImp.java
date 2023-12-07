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
@CrossOrigin(origins = "http://localhost:4200")
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
       // System.out.println("user"+user);
        if(user!=null){
            newSpace.setUser((User)user);
            Space result =  spaceService.save(newSpace,(User) user);
            if(result==null){
                throw new SpaceExistException();
            }else
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
    public ResponseEntity<Space> update(@PathVariable Long id, @RequestBody SpaceDto dto, Principal principal ) throws URISyntaxException, SpaceNotFoundException {

        Space spaceExist = spaceService.get(id);
        if(spaceExist==null){
            throw new SpaceNotFoundException();
        }
        User user =(User) userService.loadUserByUsername(principal.getName());
        Space space = new Space();
        if(user!=null){
            if(user.getId() == spaceExist.getUser().getId() ){
                space.setId(spaceExist.getId());
                space.setName(dto.getName());
                space.setUser(user);
                Space result = spaceService.save(space, user);
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
            if (spaceExist == null) {
                throw new SpaceNotFoundException();
             }else if (user.getId()== spaceExist.getUser().getId() ) {
                spaceService.delete(spaceExist);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.status(403).body(null);

    }

}
