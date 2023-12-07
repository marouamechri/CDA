package com.example.cda.controllers.user.impl;

import com.example.cda.controllers.user.DocumentController;
import com.example.cda.dtos.ResponseFile;
import com.example.cda.modeles.Document;
import com.example.cda.modeles.Event;
import com.example.cda.modeles.Task;
import com.example.cda.services.DocumentService;
import com.example.cda.services.impl.EventServiceImpl;
import com.example.cda.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController
public class DocumentControllerImpl implements DocumentController {
    @Autowired
    DocumentService documentService;
    @Autowired
    EventServiceImpl eventService;
    @Autowired
    TaskServiceImpl taskService;

    @Override
    public ResponseEntity<Map<String, String>> uploadFile(MultipartFile file, String id, String action) {
        String message = "";
        try {
           Document document = documentService.save(file, id, action);

            var result = Map.of(
                    "message", message,
                    "name", document.getName()
            );
            message = "Le fichier a été téléchargé avec succès";
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    public ResponseEntity<List<ResponseFile>> getFiles() {
        List<ResponseFile> files = documentService.getFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @Override
    public ResponseEntity<List<ResponseFile>> getEventFiles(Long id) {

        Event event = eventService.get(id);
        if(event!= null){
            List<ResponseFile> files = documentService.getEventFiles(event).map(dbFile -> {
                String fileDownloadUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/files/")
                        .path(dbFile.getId())
                        .toUriString();
                return new ResponseFile(
                        dbFile.getName(),
                        fileDownloadUri,
                        dbFile.getType(),
                        dbFile.getData().length);
            }).collect(Collectors.toList());

            // Définir les en-têtes de la réponse pour indiquer le téléchargement du fichier
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.pdf");
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(files);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

    }

    @Override
    public ResponseEntity<List<ResponseFile>> getTaskFiles(Long id) {
        Task task = taskService.get(id);
        if(task!=null){
            List<ResponseFile> files = documentService.getTaskFiles(task).map(dbFile -> {
                String fileDownloadUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/files/")
                        .path(dbFile.getId())
                        .toUriString();

                return new ResponseFile(
                        dbFile.getName(),
                        fileDownloadUri,
                        dbFile.getType(),
                        dbFile.getData().length);
            }).collect(Collectors.toList());
// Définir les en-têtes de la réponse pour indiquer le téléchargement du fichier
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.pdf");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);


            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(files);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

    }

    @Override
    public ResponseEntity<byte[]> getFile(String filename) {
        Document file = documentService.getFileByName(filename);
        if(file!=null){
            System.out.println("non de fichie: "+file.getName());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.status(HttpStatus.OK).body(file.getData());

           /* return ResponseEntity.ok()
                    .headers(headers)
                    .body(file.getData());*/
        }else
            System.out.println("fichie vide");
        return ResponseEntity.status(HttpStatus.NOT_FOUND.ordinal()).body(null);


    }

    @Override
    public ResponseEntity<String> deleteFile(String fileName) {
        System.out.println("delete");
        String message = "";

        try {
            boolean existed = documentService.delete(fileName);
System.out.println("existed :"+existed);
            if (existed) {
                message = "Le fichier a été supprimé avec succés! ";
                return ResponseEntity.status(HttpStatus.OK).body(message);
            }

            message = "Le fichier n'existe pas! ";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } catch (Exception e) {
            message = "Impossible de supprimer le fichier!";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}

