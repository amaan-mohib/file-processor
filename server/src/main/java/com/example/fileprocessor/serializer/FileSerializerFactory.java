package com.example.fileprocessor.serializer;

import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FileSerializerFactory {
    public final CsvSerializer csvSerializer;
    public final JsonSerializer jsonSerializer;
    public final XmlSerializer xmlSerializer;

    public FileSerializer getSerializer(FileMetadata.FileType fileType) {
        return switch (fileType) {
            case CSV -> csvSerializer;
            case JSON -> jsonSerializer;
            case XML -> xmlSerializer;
        };
    }
}
