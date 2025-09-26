package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.Job;
import com.example.fileprocessor.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JobFailureService {
    private final JobRepository jobRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markJobFailed(UUID jobKey, Exception e) {
        Job job = jobRepository.findByJobKey(jobKey).orElseThrow();
        job.setStatus(Job.JobStatus.FAILED);
        job.setCompletedAt(Instant.now());
        job.setFailedReason(e.getMessage());
        jobRepository.save(job);
    }
}
