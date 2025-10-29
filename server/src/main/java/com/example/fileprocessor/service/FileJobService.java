package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.Job;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.payload.request.JobCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileJobService {
    private final JobService jobService;
    private final FileMetadataService fileService;

    @Transactional
    public List<Job> uploadAndRunJobs(List<JobCreateDto> files, String query, User user) throws RuntimeException {
        try {
            List<FileMetadata> uploadedFiles = fileService.uploadFiles(user, files);
            return jobService.createAndRunJobs(uploadedFiles, query, user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<Job> rerunJob(UUID jobKey, User user) throws RuntimeException {
        Job job = jobService.findByKeyAndUser(jobKey, user).orElseThrow();
        FileMetadata file = job.getFile();
        return jobService.createAndRunJobs(List.of(file), job.getQuery(), user);
    }
}
