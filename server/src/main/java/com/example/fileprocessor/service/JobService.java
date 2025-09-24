package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.Job;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.repository.JobRepository;
import com.example.fileprocessor.storage.FileSystemStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JobService {
    private final JobRepository jobRepository;
    private final FileMetadataService fileMetadataService;
    private final FileSystemStorageService storageService;
    private final FileProcessingService fileProcessingService;

    public Job save(Job job) {
        return jobRepository.save(job);
    }

    public Optional<Job> findByKeyAndUser(UUID uuid, User user) {
        return jobRepository.findByJobKeyAndUser(uuid, user);
    }

    public void delete(Long id) {
        jobRepository.deleteById(id);
    }

    public Job create(UUID fileKey, User user, String query) {
        FileMetadata file = fileMetadataService.findByKeyAndUser(fileKey, user).orElseThrow();
        Job job = new Job();
        job.setFile(file);
        job.setUser(user);
        job.setManipulationQuery(query);
        return save(job);
    }

    @Transactional
    public Job runJob(UUID jobKey, User user) {
        Job job = jobRepository.findByJobKeyAndUser(jobKey, user).orElseThrow();
        FileMetadata file = job.getFile();
        job.setStatus(Job.JobStatus.IN_PROGRESS);
//        jobRepository.save(job);

        try (InputStream inputStream = new FileInputStream(storageService.load(file.getStoragePath()).toString())) {
            List<Map<String, Object>> result = fileProcessingService.processFile(file, inputStream);
            // save file output
            job.setStatus(Job.JobStatus.COMPLETED);
        } catch (Exception e) {
            job.setStatus(Job.JobStatus.FAILED);
            job.setFailedReason(e.getMessage());
            throw new RuntimeException(e);
        }

//        return jobRepository.save(job);
        return job;
    }
}
