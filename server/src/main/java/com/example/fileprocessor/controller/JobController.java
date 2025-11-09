package com.example.fileprocessor.controller;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.Job;
import com.example.fileprocessor.entity.RefreshToken;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.payload.request.JobCreateDto;
import com.example.fileprocessor.payload.request.JobSaveDto;
import com.example.fileprocessor.payload.response.GenericResponse;
import com.example.fileprocessor.payload.response.PageResponse;
import com.example.fileprocessor.service.FileJobService;
import com.example.fileprocessor.service.JobService;
import com.example.fileprocessor.service.RefreshTokenService;
import com.example.fileprocessor.storage.FileSystemStorageService;
import com.example.fileprocessor.util.GenericUtil;
import com.example.fileprocessor.util.SecurityUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;
    private final FileJobService fileJobService;
    private final FileSystemStorageService storageService;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/")
    public ResponseEntity<?> findAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "pageSize", defaultValue = "25") Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection
    ) {
        User currentUser = SecurityUtil.getCurrentUser();
        Page<ObjectNode> jobs = jobService.getAllJobs(currentUser, offset, pageSize, sortBy, sortDirection);
        PageResponse<ObjectNode> response = new PageResponse<>(
                jobs.getContent(),
                jobs.getNumber(),
                jobs.getSize(),
                jobs.getTotalElements(),
                jobs.getTotalPages()
        );
        return new ResponseEntity<>(new GenericResponse<>("ok", 200, response), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id:.+}")
    public ResponseEntity<GenericResponse<?>> findById(@PathVariable UUID id) {
        User currentUser = SecurityUtil.getCurrentUser();
        Job job = jobService.findByKeyAndUser(id, currentUser).orElseThrow();
        FileMetadata file = job.getFile();
        var res = new HashMap<String, Object>();
        res.put("job", job);
        var fileRes = new HashMap<String, Object>();
        fileRes.put("fileKey", file.getFileKey());
        fileRes.put("fileName", file.getFileName());
        fileRes.put("fileType", file.getFileType());
        res.put("file", fileRes);
        return new ResponseEntity<>(new GenericResponse<>("ok", 200, res), HttpStatus.OK);
    }

    @GetMapping("/output/{id:.+}")
    @ResponseBody
    public ResponseEntity<?> serveFile(@PathVariable UUID id, @RequestParam(value = "accessToken") String accessToken) {
        RefreshToken refreshToken = refreshTokenService.getRefreshToken(accessToken).orElseThrow();
        if (refreshTokenService.isExpired(refreshToken)) {
            return new ResponseEntity<>(new GenericResponse<>("Access token expired", 401), HttpStatus.UNAUTHORIZED);
        }
        User currentUser = refreshToken.getUser();
        Job job = jobService.findByKeyAndUser(id, currentUser).orElseThrow();
        if (job.getResultPath() == null) {
            return new ResponseEntity<>(new GenericResponse<>("Job has no output file", 404), HttpStatus.NOT_FOUND);
        }
        Resource file = storageService.loadAsResource("../../" + job.getResultPath());

        if (file == null) {
            return new ResponseEntity<>(new GenericResponse<>("File not found", 404), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + "output_" + file.getFilename() + "\""
                ).body(file);
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse<Job>> save(@Valid @RequestBody JobSaveDto payload) {
        User currentUser = SecurityUtil.getCurrentUser();
        Job job = jobService.create(payload.fileKey(), currentUser, payload.query());

        return new ResponseEntity<>(new GenericResponse<>("Job created", 201, job), HttpStatus.CREATED);
    }

    @PostMapping("/run/{id:.+}")
    public ResponseEntity<GenericResponse<Job>> runJob(@PathVariable UUID id) {
        User currentUser = SecurityUtil.getCurrentUser();
        Job result = jobService.runJob(id, currentUser);

        return new ResponseEntity<>(new GenericResponse<>("Job run", 200, result), HttpStatus.OK);
    }

    @PostMapping(value = "/create-run", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAndRun(@RequestParam("files") MultipartFile[] files, @RequestParam("fileTypes") String[] fileTypes, @RequestParam("query") String query) {
        User currentUser = SecurityUtil.getCurrentUser();
        List<JobCreateDto> dto =
                IntStream.range(0, files.length)
                        .mapToObj(i ->
                                new JobCreateDto(files[i], GenericUtil.getFileType(fileTypes[i]))
                        )
                        .toList();
        List<Job> result = fileJobService.uploadAndRunJobs(dto, query, currentUser);

        return new ResponseEntity<>(new GenericResponse<>("Job run", 200, result), HttpStatus.OK);
    }

    @PostMapping("/rerun/{id:.+}")
    public ResponseEntity<GenericResponse<Job>> rerunJob(@PathVariable UUID id) {
        User currentUser = SecurityUtil.getCurrentUser();
        List<Job> result = fileJobService.rerunJob(id, currentUser);
        return new ResponseEntity<>(new GenericResponse<>("Job rerun", 200, result.getFirst()), HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentJobs(@RequestParam(value = "limit", defaultValue = "25") Integer limit) {
        User currentUser = SecurityUtil.getCurrentUser();
        List<ObjectNode> jobs = jobService.getRecentJobs(currentUser, limit);
        return new ResponseEntity<>(new GenericResponse<>("Recent jobs", 200, jobs), HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElement(NoSuchElementException exc) {
        log.error("e: ", exc);
        return new ResponseEntity<>(new GenericResponse<>("Job not found", 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIO(IOException exc) {
        log.error("e: ", exc);
        return new ResponseEntity<>(new GenericResponse<>("Failed to perform operation", 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException exc) {
        log.error("e: ", exc);
        return new ResponseEntity<>(new GenericResponse<>(exc.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exc) {
        log.error("e: ", exc);
        return new ResponseEntity<>(new GenericResponse<>(GenericUtil.getRootCause(exc).getMessage(), 400), HttpStatus.BAD_REQUEST);
    }
}
