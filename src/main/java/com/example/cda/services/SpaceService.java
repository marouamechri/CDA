package com.example.cda.services;

import com.example.cda.modeles.Space;
import com.example.cda.modeles.Subject;
import com.example.cda.modeles.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface SpaceService {

    Space save(Space space, User user);
    Space get(Long id);
    Iterable<Space> getAllSpaceByUser(UserDetails userDetails);
    void delete(Long id);
    Space update(Space space, String name);
    boolean subjectExistSpace(Space space, Long idSubject);
}
