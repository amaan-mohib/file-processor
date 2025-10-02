package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.Job;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.queue.event.JobCreatedEvent;
import com.example.fileprocessor.repository.JobRepository;
import com.example.fileprocessor.storage.FileSystemStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobFailureService jobFailureService;
    private final FileMetadataService fileMetadataService;
    private final FileSystemStorageService storageService;
    private final FileProcessingService fileProcessingService;
    private final ApplicationEventPublisher eventPublisher;

    public Job save(Job job) {
        return jobRepository.save(job);
    }

    public Optional<Job> findByKeyAndUser(UUID uuid, User user) {
        return jobRepository.findByJobKeyAndUser(uuid, user);
    }

    private List<ObjectNode> getJobsWithFileName(List<Job> jobs) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return jobs.stream().map(job -> {
            ObjectNode node = mapper.valueToTree(job);
            node.put("fileName", job.getFile().getFileName());
            return node;
        }).toList();
    }

    public List<ObjectNode> getRecentJobs(User user, Integer limit) {
        List<Job> jobs = jobRepository.findByUserOrderByCreatedAtDesc(user, Limit.of(limit));
        return getJobsWithFileName(jobs);
    }

    public Page<ObjectNode> getAllJobs(User user, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Objects.equals(sortDirection, "asc") ?
                        Sort.by(sortBy).ascending() :
                        Sort.by(sortBy).descending()
        );
        var jobs = jobRepository.findByUser(user, pageable);
        var content = getJobsWithFileName(jobs.toList());
        return new PageImpl<>(content, pageable, jobs.getTotalElements());
    }

    public void delete(Long id) {
        jobRepository.deleteById(id);
    }

    public Job create(UUID fileKey, User user, String query) {
        FileMetadata file = fileMetadataService.findByKeyAndUser(fileKey, user).orElseThrow();
        Job job = new Job();
        job.setFile(file);
        job.setUser(user);
        job.setQuery(query);
        return save(job);
    }

    @Transactional
    public List<Job> createAndRunJobs(List<FileMetadata> files, String query, User user) {
        List<Job> jobs = new ArrayList<>();
        for (FileMetadata file : files) {
            Job newJob = create(file.getFileKey(), user, query);
            jobs.add(newJob);
            eventPublisher.publishEvent(new JobCreatedEvent(newJob.getJobKey()));
        }
        return jobs;
    }

    @Transactional
    public Job runJob(UUID jobKey, User user) {
        Job job = user == null ?
                jobRepository.findByJobKey(jobKey).orElseThrow() :
                jobRepository.findByJobKeyAndUser(jobKey, user).orElseThrow();
        if (job.getStatus() != Job.JobStatus.PENDING) {
            throw new IllegalStateException("Job is not in PENDING status");
        }
        if (user == null) {
            user = job.getUser();
        }
        FileMetadata file = job.getFile();
        job.setStatus(Job.JobStatus.IN_PROGRESS);
        job.setStartedAt(Instant.now());
        jobRepository.save(job);

        try (InputStream inputStream = new FileInputStream(storageService.load(file.getStoragePath()).toString())) {
            List<Map<String, Object>> result = fileProcessingService.processFile(file, inputStream, job.getQuery());
            String outputPath = fileProcessingService.dumpOutput(result, file, user.getId());

            job.setResultPath(outputPath);
            job.setStatus(Job.JobStatus.COMPLETED);
            job.setCompletedAt(Instant.now());
        } catch (Exception e) {
            jobFailureService.markJobFailed(jobKey, e);
            throw new RuntimeException(e);
        }

        return jobRepository.save(job);
    }
}
