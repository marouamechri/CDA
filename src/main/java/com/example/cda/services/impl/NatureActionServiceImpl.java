package com.example.cda.services.impl;

import com.example.cda.modeles.NatureAction;
import com.example.cda.repositorys.NatureActionRepository;
import com.example.cda.services.NatureActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class NatureActionServiceImpl implements NatureActionService {
    @Autowired
    NatureActionRepository natureActionRepository;
    @Override
    public NatureAction save(NatureAction natureEvent) {
        NatureAction natureExist= findByTitle(natureEvent.getTitle());
        return   natureActionRepository.save(natureEvent);
    }

    @Override
    public NatureAction get(int id) {
        return   natureActionRepository.findById(id).orElse(null);
    }

    @Override
    public List<NatureAction> getAll() {

        Iterable<NatureAction>  iterableList =  natureActionRepository.findAll();
        List<NatureAction> result = new ArrayList<>();
        for (NatureAction nature: iterableList) {
            result.add(nature);
        }
        return result;
    }

    @Override
    public List<NatureAction> getListEvent() {
        Iterable<NatureAction>  iterableList =  natureActionRepository.findAll();
        List<NatureAction> result = new ArrayList<>();
        for (NatureAction nature: iterableList) {
            if(!(nature.getTitle().equals("Renouvellement")))
            result.add(nature);
        }
        return result;
    }

    @Override
    public void delete(NatureAction natureEvent) {
        natureActionRepository.delete(natureEvent);
    }

    @Override
    public NatureAction update(NatureAction natureNew, NatureAction natureExist) {
        natureExist.setTitle(natureNew.getTitle());
        return natureActionRepository.save(natureExist);
    }

    @Override
    public NatureAction findByTitle(String title) {
        return natureActionRepository.findByTitle(title);
    }

}
