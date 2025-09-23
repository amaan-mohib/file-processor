package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileMetadataService {
    private final FileMetadataRepository fileMetadataRepository;

    public FileMetadata save(FileMetadata fileMetadata) {
        return fileMetadataRepository.save(fileMetadata);
    }

    public Optional<FileMetadata> findById(Long id) {
        return fileMetadataRepository.findById(id);
    }

    public List<FileMetadata> findByUser(User user) {
        return fileMetadataRepository.findByUser(user);
    }

    public Optional<FileMetadata> findByIdAndUser(Long id, User user) {
        return fileMetadataRepository.findByIdAndUser(id, user);
    }

    public void deleteFile(Long id) {
        fileMetadataRepository.deleteById(id);
    }
}
