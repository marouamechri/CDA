package com.example.cda.services.impl;

import com.example.cda.dtos.EventDto;
import com.example.cda.modeles.*;
import com.example.cda.repositorys.TaskRepository;
import com.example.cda.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EventServiceImpl eventService;

    @Autowired
    ConsultationServiceImpl consultationService;
    @Autowired
    AnalysisServiceImpl analysisService;
    @Autowired
    TreatmentServiceIpm treatmentService;
    @Autowired
    DoctorUserServiceImpl doctorUserService;
    @Autowired
    MedicalSpecialtiesServicesImpl medicalSpecialtiesServices;
    @Autowired
    SubSubjectServiceImp subSubjectServiceImp;

    //state 0 : task non valide, state 1: task en cours, state:2 task valide
    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task get(Long idTask) {
        return taskRepository.findById(idTask).orElse(null);
    }

    @Override
    public List<Task> getAllTaskByEvent(Event event) {
        Iterable<Task> tasks = taskRepository.findAll();
        List<Task> result = new ArrayList<>();
        for (Task t:tasks) {
            if (t.getEvent().getId() == event.getId()) {
                result.add(t);
            }

        }
        return result;
    }

    @Override
    public void delete(Long idTask) {
        Task task = taskRepository.findById(idTask).orElse(null);
        if(task!=null){
            taskRepository.delete(task);
        }
    }

    @Override
    public Task validTask(Task task, EventDto dto) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Event event = task.getEvent();
        Event newEvent = new Event(format.parse(dto.getDate()), false, event.getSubSubject(), event.getNatureAction());

        if(task.getNatureAction().getTitle().equals("Renovellement")){
            String natureAction = newEvent.getNatureAction().getTitle();
            if(natureAction!=null){
                if(natureAction.equals("Consultation")){
                    DoctorUser doctor = doctorUserService.get(dto.getDoctor());
                    MedicalSpecialties medicalSpecialties = medicalSpecialtiesServices.get(dto.getMedicalSpecialties());
                    if(doctor!=null&&medicalSpecialties!=null) {
                        Consultation consultation = new Consultation(newEvent, doctor, medicalSpecialties);
                        try{
                             Event eventFinal =  consultationService.save(consultation);
                            //state 0 : task non valide, state 1: task en cours, state:2 task valide
                            task.setState(1);
                            task.setEventValidate(eventFinal);
                          return   taskRepository.save(task);
                        }catch (Exception e){
                            throw  e;
                        }
                    }else return null;

                } else if (natureAction.equals("Analyse")) {
                    try{
                        Analysis analysis = new Analysis(newEvent);
                        Event eventFinal = analysisService.save(analysis);
                        task.setState(1);
                        task.setEventValidate(eventFinal);
                        return   taskRepository.save(task);

                    }catch (Exception e){
                        throw e;
                    }

                } else if (natureAction.equals("Traitement")) {
                    try{
                        Treatment treatment = new Treatment(newEvent, format.parse(dto.getDateFin()) );
                        Event eventFinal = treatmentService.save(treatment);
                        task.setState(1);
                        task.setEventValidate(eventFinal);
                        return   taskRepository.save(task);

                    }catch (Exception e){
                        throw e;
                    }

                }else return null;

            }


        }else if(task.getNatureAction().getTitle().equals("Consultation")){

            DoctorUser doctor = doctorUserService.get(dto.getDoctor());
            MedicalSpecialties medicalSpecialties = medicalSpecialtiesServices.get(dto.getMedicalSpecialties());
            if(doctor!=null&&medicalSpecialties!=null) {
                Consultation consultation = new Consultation(newEvent, doctor, medicalSpecialties);
                try{
                    Event eventFinal =  consultationService.save(consultation);
                    //state 0 : task non valide, state 1: task en cours, state:2 task valide
                    task.setState(1);
                    task.setEventValidate(eventFinal);
                    return   taskRepository.save(task);
                }catch (Exception e){
                    throw  e;
                }


        } else if (task.getNatureAction().getTitle().equals("Analyse")) {
                try{
                    Analysis analysis = new Analysis(newEvent);
                    //ajout de la resultat pour que l'event soit valider
                    Event eventFinal = analysisService.save(analysis);
                    task.setState(1);
                    task.setEventValidate(eventFinal);
                    return   taskRepository.save(task);

                }catch (Exception e){
                    throw e;
                }
            }

        } else if (task.getNatureAction().getTitle().equals("Traitement")) {
            try{
                Treatment treatment = new Treatment(newEvent,format.parse(dto.getDateFin()));
                Event eventFinal = treatmentService.save(treatment);
                task.setState(1);
                task.setEventValidate(eventFinal);
                return   taskRepository.save(task);

            }catch (Exception e){
                throw e;
            }

        } else if (task.getNatureAction().getTitle().equals("Consignes")) {
            task.setState(2);
            return   taskRepository.save(task);
        }
        return null;
    }
    //fonction permet de tester la validité des @PathVariable qui sont envoyé dans URL
    @Override
    public boolean validInformation(User user,Long idSpace, Long idSubject, Long idSubSubject, Long idEvent) {
        Boolean valid1 = eventService.validInformation(user,idSpace, idSubject, idSubSubject);
        Boolean valid2= false;
        SubSubject subSubject = subSubjectServiceImp.get(idSubSubject);
        Event event  = eventService.get(idEvent);
        if(event!=null && subSubject!=null && subSubject.getEvents()!=null){
            for (Event e: subSubject.getEvents()){
                if(e.getId()==event.getId()){
                    valid2= true;
                }
            }
        }
        if(valid1 && valid2){
            return true;
        }
        return false;
    }

}
