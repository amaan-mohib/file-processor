package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.payload.request.FileSaveDto;
import com.example.fileprocessor.payload.request.JobCreateDto;
import com.example.fileprocessor.repository.FileMetadataRepository;
import com.example.fileprocessor.storage.StorageService;
import com.example.fileprocessor.util.GenericUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public Optional<FileMetadata> findByIdAndUser(Long id, User user) {
        return fileMetadataRepository.findByIdAndUser(id, user);
    }

    public Optional<FileMetadata> findByKeyAndUser(UUID uuid, User user) {
        return fileMetadataRepository.findByFileKeyAndUser(uuid, user);
    }

    public Page<?> getAllFiles(User user, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Objects.equals(sortDirection, "asc") ?
                        Sort.by(sortBy).ascending() :
                        Sort.by(sortBy).descending()
        );
        var files = fileMetadataRepository.findByUser(user, pageable);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        var res = files.stream().map(file -> {
            var obj = new HashMap<String, Object>();
            obj.put("id", file.getId());
            obj.put("fileKey", file.getFileKey());
            obj.put("fileName", file.getFileName());
            obj.put("fileType", file.getFileType());
            obj.put("originalSize", file.getOriginalSize());
            obj.put("createdAt", file.getCreatedAt());
            return mapper.valueToTree(obj);
        }).toList();
        return new PageImpl<>(res, pageable, files.getTotalElements());
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
