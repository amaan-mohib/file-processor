package com.example.fileprocessor.storage;

import com.example.fileprocessor.payload.request.FileSaveDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

    FileSaveDto save(MultipartFile file, Long userId);

    Stream<Path> loadAll();

    Path load(String path);

    Resource loadAsResource(String path);

    void delete(String path);

    void deleteAll();
}
