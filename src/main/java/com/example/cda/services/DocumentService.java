package com.example.cda.services;

import com.example.cda.modeles.Document;
import com.example.cda.modeles.Event;
import com.example.cda.modeles.Task;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
//import org.springframework.core.io.Resource;

public interface DocumentService {
    Document save(MultipartFile file, String id, String action) throws IOException;
   // Document uploadFiles(String nameFile, String UriFile);
   public Document getFileByName(String filename);

    public boolean delete(String fileName);

   public Stream<Document> getFiles();
    public Stream<Document> getTaskFiles(Task task);
    public Stream<Document> getEventFiles(Event event);


}
