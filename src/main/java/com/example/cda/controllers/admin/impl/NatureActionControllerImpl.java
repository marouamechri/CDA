package com.example.cda.controllers.admin.impl;

import com.example.cda.controllers.admin.NatureActionController;
import com.example.cda.dtos.NatureActionDto;
import com.example.cda.exceptions.NatureExistException;
import com.example.cda.exceptions.NatureNotFoundException;
import com.example.cda.modeles.NatureAction;
import com.example.cda.services.NatureActionService;
import com.example.cda.services.impl.NatureActionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@RestController
public class NatureActionControllerImpl implements NatureActionController {

    @Autowired
    NatureActionService natureActionService = new NatureActionServiceImpl();

    @Override
    public ResponseEntity<NatureAction> createNatureAction(NatureActionDto dto) throws NatureExistException, URISyntaxException {
            if(natureActionService.findByTitle(dto.getTitle())!=null){
                throw new NatureExistException();
            }
            NatureAction natureAction = new NatureAction() ;
            natureAction.setTitle(dto.getTitle());
            NatureAction result =  natureActionService.save(natureAction);
            String NatureActionURI = "/natureAction/"+result.getId();
            return ResponseEntity.created(new URI(NatureActionURI)).body(result);
        }

    @Override
    public ResponseEntity<NatureAction> getNatureAction(int id) throws NatureNotFoundException {
        NatureAction natureAction = natureActionService.get(id);
        if(natureAction==null){
            throw  new NatureNotFoundException();
        }
        return ResponseEntity.status(200).body(natureAction);
    }

    @Override
    public ResponseEntity<List<NatureAction>> getAllNatureEvent() {
        return ResponseEntity.status(200).body(natureActionService.getListEvent());

    }

    @Override
    public ResponseEntity<List<NatureAction>> getAllNatureTask() {
        return ResponseEntity.status(200).body(natureActionService.getAll());

    }

    @Override
    public ResponseEntity<?> deleteNatureAction(int id) throws NatureNotFoundException {
        NatureAction natureAction = natureActionService.get(id);
        if(natureAction==null){
            throw new NatureNotFoundException();
        }
        natureActionService.delete(natureAction);
        return ResponseEntity.noContent().build();// Sending a 204 HTTP status code
    }

    @Override
    public ResponseEntity<NatureAction> updateNatureAction(NatureActionDto dto, int id) throws NatureNotFoundException {
        NatureAction natureExist = natureActionService.get(id);
        if(natureExist == null){
            throw new NatureNotFoundException();
        }
        NatureAction newNatureAction = new NatureAction();
        newNatureAction.setTitle(dto.getTitle());
        NatureAction result = natureActionService.update(newNatureAction, natureExist);
        return ResponseEntity.ok(result);
    }
}
