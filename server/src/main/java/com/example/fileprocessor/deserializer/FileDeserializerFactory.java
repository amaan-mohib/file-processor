package com.example.fileprocessor.deserializer;

import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FileDeserializerFactory {
    public CsvDeserializer csvDeserializer;

    public FileDeserializer getDeserializer(FileMetadata.FileType fileType) {
        return switch (fileType) {
            case CSV -> csvDeserializer;
            // TODO: implement JSON and XML parser
            case JSON, XML -> throw new IllegalArgumentException("Unknown file type: " + fileType);
        };
    }
}
