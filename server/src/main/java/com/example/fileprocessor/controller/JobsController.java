package com.example.fileprocessor.controller;

import com.example.fileprocessor.dto.JobSaveDto;
import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.Jobs;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.response.GenericResponse;
import com.example.fileprocessor.service.FileMetadataService;
import com.example.fileprocessor.service.JobsService;
import com.example.fileprocessor.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobsController {
    private final JobsService jobsService;
    private final FileMetadataService fileMetadataService;

    @GetMapping("/{id:.+}")
    public ResponseEntity<GenericResponse<Jobs>> findById(@PathVariable UUID id) {
        User currentUser = SecurityUtil.getCurrentUser();
        Jobs job = jobsService.findByKeyAndUser(id, currentUser).orElseThrow();
        return new ResponseEntity<>(new GenericResponse<>("ok", 200, job), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse<Jobs>> save(@Valid @RequestBody JobSaveDto payload) {
        User currentUser = SecurityUtil.getCurrentUser();
        FileMetadata file = fileMetadataService.findByKeyAndUser(payload.fileKey(), currentUser).orElseThrow();

        Jobs job = new Jobs();
        job.setFile(file);
        job.setUser(currentUser);
        job.setManipulationQuery(payload.query());
        jobsService.save(job);

        return new ResponseEntity<>(new GenericResponse<>("Job created", 201, job),  HttpStatus.CREATED);
    }

}
