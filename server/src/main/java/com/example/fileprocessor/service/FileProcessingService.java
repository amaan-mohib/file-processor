package com.example.fileprocessor.service;

import com.example.fileprocessor.deserializer.FileDeserializer;
import com.example.fileprocessor.engine.QueryEngine;
import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.deserializer.FileDeserializerFactory;
import com.example.fileprocessor.serializer.FileSerializer;
import com.example.fileprocessor.serializer.FileSerializerFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FileProcessingService {
    private final FileDeserializerFactory fileDeserializerFactory;
    private final FileSerializerFactory fileSerializerFactory;
    private final QueryEngine queryEngine;

    public List<Map<String, Object>> processFile(FileMetadata file, InputStream inputStream, String query) throws Exception {
        FileDeserializer fileDeserializer = fileDeserializerFactory.getDeserializer(file.getFileType());
        var deserialized = fileDeserializer.deserialize(inputStream);
        return queryEngine.execute(deserialized, query);
    }

    public String dumpOutput(List<Map<String, Object>> result, FileMetadata file, Long userId) throws IOException {
        FileSerializer fileSerializer = fileSerializerFactory.getSerializer(file.getFileType());
        Path outputPath = Path.of("storage/output-dir", userId.toString());
        Files.createDirectories(outputPath);
        outputPath = outputPath.resolve(file.getFileKey() + "_" + file.getFileName());

        fileSerializer.serialize(result, outputPath);
        return outputPath.toString();
    }
}
