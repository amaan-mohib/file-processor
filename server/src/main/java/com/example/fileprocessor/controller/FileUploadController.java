package com.example.fileprocessor.controller;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.payload.request.JobCreateDto;
import com.example.fileprocessor.payload.response.GenericResponse;
import com.example.fileprocessor.service.FileMetadataService;
import com.example.fileprocessor.storage.StorageException;
import com.example.fileprocessor.storage.StorageFileNotFoundException;
import com.example.fileprocessor.storage.StorageService;
import com.example.fileprocessor.util.GenericUtil;
import com.example.fileprocessor.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileUploadController {
    private final StorageService storageService;
    private final FileMetadataService fileService;

    @GetMapping("/{id:.+}")
    @ResponseBody
    public ResponseEntity<?> serveFile(@PathVariable UUID id) {
        User currentUser = SecurityUtil.getCurrentUser();
        FileMetadata fileMetadata = fileService.findByKeyAndUser(id, currentUser).orElseThrow();
        Resource file = storageService.loadAsResource(fileMetadata.getStoragePath());

        if (file == null) {
            return new ResponseEntity<>(new GenericResponse<>("File not found", 404), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "type", defaultValue = "CSV") String type
    ) {
        User currentUser = SecurityUtil.getCurrentUser();
        JobCreateDto fileObj = new JobCreateDto();
        fileObj.setFile(file);
        fileObj.setFileType(GenericUtil.getFileType(type));
        FileMetadata createdFile = fileService.uploadFiles(currentUser, List.of(fileObj)).getFirst();
        Map<String, Object> result = new HashMap<>();
        result.put("key", createdFile.getFileKey());
        result.put("type", createdFile.getFileType().toString());
        result.put("name", createdFile.getFileName());
        result.put("size", createdFile.getOriginalSize());
        result.put("createdAt", createdFile.getCreatedAt());

        return ResponseEntity.ok(new GenericResponse<>("File successfully uploaded!", 200, result));
    }

    @DeleteMapping("/{id:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable UUID id) {
        User currentUser = SecurityUtil.getCurrentUser();
        FileMetadata fileMetadata = fileService.findByKeyAndUser(id, currentUser).orElseThrow();

        storageService.delete(fileMetadata.getStoragePath());
        fileService.deleteFile(fileMetadata.getId());
        return ResponseEntity.ok(new GenericResponse<>("File successfully deleted!"));
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        log.error("e: ", exc);
        return new ResponseEntity<>(new GenericResponse<>("File not found", 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElement(NoSuchElementException exc) {
        log.error("e: ", exc);
        return new ResponseEntity<>(new GenericResponse<>("File not found", 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageException(StorageException exc) {
        log.error("e: ", exc);
        return new ResponseEntity<>(
                new GenericResponse<>(
                        "Failed to perform operation, the file may have been moved or does not exist",
                        400
                ),
                HttpStatus.BAD_REQUEST
        );
    }
}
