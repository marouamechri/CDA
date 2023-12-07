package com.example.cda.services.impl;

import com.example.cda.exceptions.FileUploadException;
import com.example.cda.modeles.Document;
import com.example.cda.modeles.Event;
import com.example.cda.modeles.Task;
import com.example.cda.repositorys.DocumentRepository;
import com.example.cda.services.DocumentService;
import com.example.cda.services.EventService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.IMarkerFactory;
@Service
public class DocumentsServiceImp implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    EventServiceImpl eventService;
    @Autowired
    TaskServiceImpl taskService;
    private static final Logger logger = LoggerFactory.getLogger(DocumentsServiceImp.class);

    private final Path root = Paths.get("uploads");


    @Override
    public Document save(MultipartFile file, String id, String action) throws IOException {
        Long idAction = Long.parseLong(id);
        //généré le nom unique
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring((originalFileName.lastIndexOf(".")));
        String uniqueFileName = UUID.randomUUID().toString()+ fileExtension;
        String fileName = StringUtils.cleanPath(uniqueFileName);
        Document document = new Document(fileName, file.getContentType(), file.getBytes());

        if(action.equals("event")){
         Event event = eventService.get(idAction);
         List<Event> events = new ArrayList<>();
         events.add(event);
         document.setEvents(events);

        }else if(action.equals("task")){
            Task task = taskService.get(idAction);
            List<Task> tasks = new ArrayList<>();
            document.setTasks(tasks);
        }

        return documentRepository.save(document);

    }



   @Override
    public Stream<Document> getFiles() {
       Spliterator spliterator = documentRepository.findAll().spliterator();

       return StreamSupport.stream(spliterator, false);
    }

    @Override
    public Stream<Document> getTaskFiles(Task task) {
        Spliterator spliterator = task.getPrescription().spliterator();

        return StreamSupport.stream(spliterator, false);

    }

    @Override
    public Stream<Document> getEventFiles(Event event) {
        Spliterator spliterator = event.getPrescription().spliterator();

        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public Document getFileByName(String fileName) {
        return  documentRepository.findByName(fileName);
    }

    @Override
    public boolean delete(String fileName) {
       Document document = documentRepository.findByName(fileName);
       if(document!=null){
           documentRepository.delete(document);
           return true;
       }else
           return false;
    }



}
