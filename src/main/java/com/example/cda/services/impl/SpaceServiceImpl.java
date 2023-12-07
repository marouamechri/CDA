package com.example.cda.services.impl;

import com.example.cda.modeles.Space;
import com.example.cda.modeles.Subject;
import com.example.cda.modeles.User;
import com.example.cda.repositorys.SpaceRepository;
import com.example.cda.repositorys.UserRepository;
import com.example.cda.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceServiceImpl implements SpaceService {
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Space save(Space space, User user ) {

        Iterable<Space> spaces = getAllSpaceByUser(user);
        Boolean spaceExist=false;

        if(spaces!=null){
            for (Space e : spaces) {
               if(e.getName().equals(space.getName())){
                   System.out.println( e.getName() + " "+ space.getName());
                   spaceExist = true;
               }
            }
        }
        System.out.println("SpaceExiste: "+ spaceExist);
        if(spaceExist){
            return null;
        }else
        return  spaceRepository.save(space);

    }

    @Override
    public Space get(Long id) {
        return spaceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Space> getAllSpaceByUser(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());

        if(user!=null){
            return user.getSpaces();

        }
        else return null;
    }

    @Override
    public void delete(Space space) {         spaceRepository.delete( space);

    }

    @Override
    public boolean subjectExistSpace(Space space, Long idSubject) {
        List<Subject> subjects = space.getSubjects();
        boolean exist = false;
        if(subjects!=null){
            for (Subject s:subjects) {
                if(s.getId() == idSubject){
                    exist= true;
                }
            }
        }
        return exist;
    }

}
