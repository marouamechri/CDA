package com.example.cda.controllers.user.impl;

import com.example.cda.dtos.DoctorUserDto;
import com.example.cda.exceptions.*;
import com.example.cda.modeles.*;
import com.example.cda.services.DoctorUserService;
import com.example.cda.services.MedicalSpecialtiesService;
import com.example.cda.services.SpaceService;
import com.example.cda.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DoctorUserController implements com.example.cda.controllers.user.DoctorUserController {
    @Autowired
    DoctorUserService doctorUserService;
    @Autowired
    SpaceService spaceService;
    @Autowired
    UserService userService;

    @Autowired
    MedicalSpecialtiesService medicalSpecialtiesService;


   @Override
    public ResponseEntity<DoctorUser> save(DoctorUserDto dto, Principal principal, Long idSpace) throws DoctorUserExistException, URISyntaxException, SpaceNotFoundException {
        Space space= spaceService.get(idSpace);
        MedicalSpecialties medicalSpecialty = medicalSpecialtiesService.get(dto.getMedicalSpecialty());
        UserDetails user = userService.loadUserByUsername(principal.getName());
        boolean DoctorExist = false;

        if((space!=null)&& (medicalSpecialty!=null) && (space.getUser().getUsername().equals(user.getUsername())  )){
            List<DoctorUser> doctorUserList = doctorUserService.getAllDoctorByUser(user);
            if(doctorUserList!=null){
                for (DoctorUser d:doctorUserList) {
                    if(d.getName().equals(dto.getName()))
                    {
                        DoctorExist = true;
                    }
                }
            }
            if(!DoctorExist){
                DoctorUser doctorUser = new DoctorUser();
                List<Space>spaces = new ArrayList<>();
                List<MedicalSpecialties>medicalSpecialties = new ArrayList<>();
                medicalSpecialties.add(medicalSpecialty);
                spaces.add(space);
                doctorUser.setName(dto.getName());
                doctorUser.setAddress(dto.getAddress());
                doctorUser.setPhone(dto.getPhone());
                doctorUser.setSpaces(spaces);
                doctorUser.setMedicalSpecialties(medicalSpecialties);

                DoctorUser result = doctorUserService.save(doctorUser);

                return ResponseEntity.status(201).body(result);
            }else {
                throw new DoctorUserExistException();
            }

        }else
            return ResponseEntity.status(403).body(null);

    }

    @Override
    public ResponseEntity<DoctorUser> get(Long idSpace, Principal principal, Long idDoctor) throws URISyntaxException, DoctorUserNotFoundException, SpaceNotFoundException {

        Space space= spaceService.get(idSpace);
        if(space==null){
            throw new SpaceNotFoundException();
        }else {
            UserDetails user = userService.loadUserByUsername(principal.getName());
            if(space.getUser().getUsername().equals(user.getUsername())){
                DoctorUser doctorUser= doctorUserService.get(idDoctor);
                if(doctorUser!=null){
                    return ResponseEntity.status(200).body(doctorUser);
                }else {
                    throw new DoctorUserNotFoundException();
                }

            }else
                return ResponseEntity.status(403).body(null);
        }

    }

    @Override
    public ResponseEntity<Iterable<DoctorUser>> getAllDoctorBySpace(Long idSpace, Principal principal) throws SpaceNotFoundException {
        Space space= spaceService.get(idSpace);
        UserDetails user = userService.loadUserByUsername(principal.getName());

        if((space!=null) && (space.getUser().getUsername().equals(user.getUsername())  )){
            return ResponseEntity.status(200).body(doctorUserService.getAllUserBySpace(space));

        }else
            throw new SpaceNotFoundException();

        }

    @Override
    public ResponseEntity<Iterable<DoctorUser>> getAllDoctorByUser(Principal principal) throws UserNotFoundException {
        UserDetails userDetails = userService.loadUserByUsername(principal.getName());
        return ResponseEntity.status(200).body(doctorUserService.getAllDoctorByUser(userDetails));
    }

    @Override
    public ResponseEntity<DoctorUser> update(@PathVariable Long idDoctor, @PathVariable Long idSpace, @RequestBody DoctorUserDto dto, Principal principal) throws URISyntaxException, DoctorUserNotFoundException, SpaceNotFoundException, DoctorUserExistException {
        Space space= spaceService.get(idSpace);
        MedicalSpecialties medicalSpecialty = medicalSpecialtiesService.get(dto.getMedicalSpecialty());
        UserDetails user = userService.loadUserByUsername(principal.getName());
        DoctorUser doctorUser = doctorUserService.get(idDoctor);
        boolean DoctorExist = false;

        if((space!=null)&& (doctorUser!=null)&&(medicalSpecialty!=null) && (space.getUser().getUsername().equals(user.getUsername())  )){
            System.out.println("debut updateDoctor");
            List<DoctorUser> doctorUserList = doctorUserService.getAllDoctorByUser(user);
            if(doctorUserList!=null){
                for (DoctorUser d:doctorUserList) {
                    if(d.getName().equals(dto.getName()))
                    {
                        DoctorExist = true;
                    }
                }
            }
            if(!DoctorExist){

                doctorUser.setName(dto.getName());
                doctorUser.setAddress(dto.getAddress());
                doctorUser.setPhone(dto.getPhone());

                DoctorUser result = doctorUserService.save(doctorUser);

                return ResponseEntity.status(201).body(result);
            }else {
                throw new DoctorUserExistException();
            }

        }else
            return ResponseEntity.status(403).body(null);

    }

    @Override
    public ResponseEntity<?> delete(Long idDoctor, Principal principal) throws URISyntaxException, DoctorUserNotFoundException {
        return null;
    }

    @Override
    public ResponseEntity<List<MedicalSpecialties>> getMedicalSpecialtiesByDoctor(Long idSpace, Principal principal, Long idDoctor) throws URISyntaxException, DoctorUserNotFoundException, SpaceNotFoundException {
           Space space= spaceService.get(idSpace);
           if(space==null){
               throw new SpaceNotFoundException();
           }else {
               UserDetails user = userService.loadUserByUsername(principal.getName()) ;
               if(space.getUser().getUsername().equals(user.getUsername())){
                   DoctorUser doctorUser = doctorUserService.get(idDoctor) ;
                   if(doctorUser!=null){
                       List<MedicalSpecialties> medicalSpecialties= medicalSpecialtiesService.getSpecialitiesByDoctor(doctorUser);
                       return ResponseEntity.status(200).body(medicalSpecialties);
                   }else {
                       throw new DoctorUserNotFoundException();                      
                   }

               }else
                   return ResponseEntity.status(403).body(null);
           }



    }
}
