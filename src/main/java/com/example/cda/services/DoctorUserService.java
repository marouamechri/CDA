package com.example.cda.services;

import com.example.cda.modeles.DoctorUser;
import com.example.cda.modeles.MedicalSpecialties;
import com.example.cda.modeles.Space;
import com.example.cda.modeles.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface DoctorUserService {

    DoctorUser save(DoctorUser doctorUser);
    DoctorUser get(Long id);
    List<DoctorUser> getAllDoctorByUser(UserDetails userDetails);
    List<DoctorUser> getAllUserBySpace(Space space);
    void delete(Long id);
    boolean attacheDoctorToSpeciality(DoctorUser doctorUser, MedicalSpecialties medicalSpecialties);
    boolean detachDoctorToSpeciality(DoctorUser doctorUser, MedicalSpecialties speciality);
    boolean attachDoctorToSpace(DoctorUser doctorUser, Space space);
    boolean  detachDoctorToSpace(DoctorUser doctorUser, Space space);


}
