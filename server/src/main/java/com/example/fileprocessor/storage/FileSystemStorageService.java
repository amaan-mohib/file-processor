package com.example.fileprocessor.storage;

import com.example.fileprocessor.dto.FileSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        if (properties.getLocation().trim().isEmpty()) {
            throw new StorageFileNotFoundException(String.format("Cannot find location %s", properties.getLocation()));
        }
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public FileSaveDto save(MultipartFile file, Long userId) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                throw new StorageException("Failed to store a file without name.");
            }
            UUID uuid = UUID.randomUUID();
            fileName = uuid + "_" + file.getOriginalFilename();
            Path targetParentPath = Paths.get(userId.toString());
            Files.createDirectories(this.rootLocation.resolve(targetParentPath));
            targetParentPath = targetParentPath.resolve(fileName);

            Path destinationPath = this.rootLocation.resolve(targetParentPath).normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }
            return new FileSaveDto(targetParentPath.toString(), uuid);
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try (Stream<Path> paths = Files.walk(this.rootLocation, 1)) {
            return paths
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String path) {
        return rootLocation.resolve(path).normalize();
    }

    @Override
    public Resource loadAsResource(String path) {
        try {
            Path file = load(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + path);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + path, e);
        }
    }

    @Override
    public void delete(String path) {
        try {
            Path file = load(path);
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Could not delete file: " + path, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
