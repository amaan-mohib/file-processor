package com.example.fileprocessor.serializer;

import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FileSerializerFactory {
    public CsvSerializer csvSerializer;

    public FileSerializer getSerializer(FileMetadata.FileType fileType) {
        return switch (fileType) {
            case CSV -> csvSerializer;
            // TODO: implement JSON and XML parser
            case JSON, XML -> throw new IllegalArgumentException("Unknown file type: " + fileType);
        };
    }
}
