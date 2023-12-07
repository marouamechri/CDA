package com.example.cda.services;

import com.example.cda.modeles.NatureAction;

import java.util.List;

public interface NatureActionService {
    NatureAction save(NatureAction natureAction);
    NatureAction get(int id);
    List<NatureAction> getAll();
    List<NatureAction>getListEvent();
    void  delete (NatureAction natureAction);
    NatureAction update(NatureAction natureAction, NatureAction natureExist);
    NatureAction findByTitle(String title);
    int getId(String title);

}
