package com.example.fileprocessor.repository;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.Job;
import com.example.fileprocessor.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByJobKeyAndUser(UUID jobKey, User user);
    Optional<Job> findByJobKey(UUID jobKey);
    List<Job> findByStatusAndUser(Job.JobStatus status, User user, Pageable pageable);
    List<Job> findByFileAndUser(FileMetadata file, User user, Pageable pageable);
    List<Job> findByUser(User user, Pageable pageable);
}
