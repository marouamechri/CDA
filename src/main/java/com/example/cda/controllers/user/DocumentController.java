package com.example.cda.controllers.user;

import com.example.cda.dtos.ResponseFile;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@PreAuthorize("hasAuthority(\"USER\")")
@RequestMapping(path = "/user/files")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public interface DocumentController {
    @RequestMapping(value="/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("id") String id, @RequestParam("action") String action);
    // @PostMapping(value = "/fileUpload", consumes = MULTIPART_FORM_DATA_VALUE)
    @GetMapping("/")
   public ResponseEntity<List<ResponseFile>> getFiles();
    @GetMapping("/event/{id}")
    public ResponseEntity<List<ResponseFile>> getEventFiles(@PathVariable Long id);

    @GetMapping("/task/{id}")
    public ResponseEntity<List<ResponseFile>> getTaskFiles(@PathVariable Long id);
    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename);

    @DeleteMapping("/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName);

}
