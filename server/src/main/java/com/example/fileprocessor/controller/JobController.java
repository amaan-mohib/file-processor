package com.example.fileprocessor.controller;

import com.example.fileprocessor.dto.JobSaveDto;
import com.example.fileprocessor.entity.Job;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.response.GenericResponse;
import com.example.fileprocessor.service.JobService;
import com.example.fileprocessor.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;

    @GetMapping("/{id:.+}")
    public ResponseEntity<GenericResponse<Job>> findById(@PathVariable UUID id) {
        User currentUser = SecurityUtil.getCurrentUser();
        Job job = jobService.findByKeyAndUser(id, currentUser).orElseThrow();
        return new ResponseEntity<>(new GenericResponse<>("ok", 200, job), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse<Job>> save(@Valid @RequestBody JobSaveDto payload) {
        User currentUser = SecurityUtil.getCurrentUser();
        Job job = jobService.create(payload.fileKey(), currentUser, payload.query());

        return new ResponseEntity<>(new GenericResponse<>("Job created", 201, job),  HttpStatus.CREATED);
    }

    @PostMapping("/run/{id:.+}")
    public ResponseEntity<GenericResponse<Job>> runJob(@PathVariable UUID id) {
        User currentUser = SecurityUtil.getCurrentUser();
        Job result = jobService.runJob(id, currentUser);

        return new ResponseEntity<>(new GenericResponse<>("Job run", 200, result), HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElement(NoSuchElementException exc) {
        return new ResponseEntity<>(new GenericResponse<>("Job not found", 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIO(IOException exc) {
        return new ResponseEntity<>(new GenericResponse<>("Failed to perform operation", 400), HttpStatus.BAD_REQUEST);
    }
}
