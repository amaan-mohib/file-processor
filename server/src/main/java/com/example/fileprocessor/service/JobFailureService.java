package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.Job;
import com.example.fileprocessor.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JobFailureService {
    private final JobRepository jobRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markJobFailed(UUID jobKey, Exception e) {
        Optional<Job> job = jobRepository.findByJobKey(jobKey);
        job.ifPresent(j -> {
            j.setStatus(Job.JobStatus.FAILED);
            j.setCompletedAt(Instant.now());
            j.setFailedReason(e.getMessage());
            jobRepository.save(j);
        });
    }
}
