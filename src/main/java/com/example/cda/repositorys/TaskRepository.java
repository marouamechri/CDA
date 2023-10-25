package com.example.cda.repositorys;

import com.example.cda.modeles.Event;
import com.example.cda.modeles.Task;
import jdk.jfr.Registered;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task getByEventValidate(Event eventValidate);
}
