package com.example.fileprocessor.repository;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Page<FileMetadata> findByUser(User user, Pageable pageable);

    Optional<FileMetadata> findByIdAndUser(Long id, User user);

    Optional<FileMetadata> findByFileKeyAndUser(UUID fileKey, User user);
}
