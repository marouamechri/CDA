package com.example.cda.services.impl;

import com.example.cda.modeles.DoctorUser;
import com.example.cda.modeles.MedicalSpecialties;
import com.example.cda.modeles.Space;
import com.example.cda.modeles.User;
import com.example.cda.repositorys.DoctorUserRepository;
import com.example.cda.repositorys.MedicalSpecialtiesRepository;
import com.example.cda.repositorys.SpaceRepository;
import com.example.cda.repositorys.UserRepository;
import com.example.cda.services.DoctorUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorUserServiceImpl implements DoctorUserService {
    @Autowired
    DoctorUserRepository doctorUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MedicalSpecialtiesRepository medicalSpecialtiesRepository;
    @Autowired
    SpaceRepository spaceRepository;


    @Override
    public DoctorUser save(DoctorUser doctorUser) {
        return doctorUserRepository.save(doctorUser);
    }

    @Override
    public DoctorUser get(Long id) {
        return doctorUserRepository.findById(id).orElse(null);
    }


    @Override
    public List<DoctorUser> getAllDoctorByUser(UserDetails userDetails) {
        User user= userRepository.findByUsername(userDetails.getUsername());
        if(user!=null){
            List<DoctorUser> doctorUsers = new ArrayList<>();
            List<Space> spaces = user.getSpaces();
            if(spaces!=null){
                for (Space s:spaces) {
                    for(DoctorUser d: s.getDoctorUsers())
                        doctorUsers.add(d);
                }
            }
            return doctorUsers;

        }else
            return null;
    }

    @Override
    public List<DoctorUser> getAllUserBySpace(Space space) {
        return space.getDoctorUsers();
    }

    @Override
    public void delete(Long id) {
        DoctorUser doctorUser = doctorUserRepository.findById(id).orElse(null);
        if(doctorUser!= null){
            doctorUserRepository.delete( doctorUser);
        }
    }

    @Override
    public boolean attacheDoctorToSpeciality(DoctorUser doctorUser, MedicalSpecialties speciality) {
            List<MedicalSpecialties> medicalSpecialties = doctorUser.getMedicalSpecialties();
            if(medicalSpecialties!=null)
            {
                for (MedicalSpecialties m:medicalSpecialties) {
                    if(m.getId()== speciality.getId()){
                        return  false;
                    }
                }
                doctorUser.getMedicalSpecialties().add(speciality);
                return true;

            }else
                doctorUser.getMedicalSpecialties().add(speciality);
                return true;

    }

    @Override
    public boolean detachDoctorToSpeciality(DoctorUser doctorUser, MedicalSpecialties speciality) {
            List<MedicalSpecialties> medicalSpecialties = doctorUser.getMedicalSpecialties();
            if(medicalSpecialties!=null){
                for (MedicalSpecialties m:medicalSpecialties) {
                    if(m.getId()== speciality.getId()) {
                        doctorUser.getMedicalSpecialties().remove(m);
                         return true;
                    }
                }

            }
            return  false;
    }

    @Override
    public boolean attachDoctorToSpace(DoctorUser doctorUser, Space space) {
            List<Space> spaces= doctorUser.getSpaces();
            if(spaces!=null){
                for (Space s:spaces) {
                    if(s.getId()==space.getId()){
                        return false;
                    }
                }
                doctorUser.getSpaces().add(space);
                return true;
            }
            doctorUser.getSpaces().add(space);
            return true;
    }

    @Override
    public boolean detachDoctorToSpace(DoctorUser doctorUser, Space space) {
        List<Space> spaces= doctorUser.getSpaces();

        if (spaces!=null){
            System.out.println("space not null");
            for (Space s:spaces) {
                if(s.getId()==space.getId()){
                    doctorUser.getSpaces().remove(s);
                    return true;
                }
            }

        }
        return false;
    }
}
