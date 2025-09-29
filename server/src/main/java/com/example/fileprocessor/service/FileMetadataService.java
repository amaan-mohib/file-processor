package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.payload.request.FileSaveDto;
import com.example.fileprocessor.payload.request.JobCreateDto;
import com.example.fileprocessor.repository.FileMetadataRepository;
import com.example.fileprocessor.storage.StorageService;
import com.example.fileprocessor.util.GenericUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileMetadataService {
    private final FileMetadataRepository fileMetadataRepository;
    private final StorageService storageService;

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

    public Optional<FileMetadata> findByKeyAndUser(UUID uuid, User user) {
        return fileMetadataRepository.findByFileKeyAndUser(uuid, user);
    }

    @Transactional
    public List<FileMetadata> uploadFiles(User user, List<JobCreateDto> files) {
        List<FileMetadata> fileMetadataList = new ArrayList<>();
        for (var fileObj: files) {
            FileSaveDto saveResponse = storageService.save(fileObj.getFile(), user.getId());

            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.setFileKey(saveResponse.getFileKey());
            fileMetadata.setFileType(GenericUtil.getFileType(fileObj.getFileType().toString()));
            fileMetadata.setFileName(fileObj.getFile().getOriginalFilename());
            fileMetadata.setStoragePath(saveResponse.getFilePath());
            fileMetadata.setOriginalSize(fileObj.getFile().getSize());
            fileMetadata.setUser(user);

            fileMetadataList.add(save(fileMetadata));
        }
        return fileMetadataList;
    }

    public void deleteFile(Long id) {
        fileMetadataRepository.deleteById(id);
    }
}
