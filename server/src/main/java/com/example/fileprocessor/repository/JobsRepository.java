package com.example.fileprocessor.repository;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.Jobs;
import com.example.fileprocessor.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobsRepository extends JpaRepository<Jobs, Long> {
    Optional<Jobs> findByJobKeyAndUser(UUID jobKey, User user);
    List<Jobs> findByStatusAndUser(Jobs.JobStatus status, User user, Pageable pageable);
    List<Jobs> findByFileAndUser(FileMetadata file, User user, Pageable pageable);
    List<Jobs> findByUser(User user, Pageable pageable);
}
