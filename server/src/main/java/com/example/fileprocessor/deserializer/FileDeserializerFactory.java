package com.example.fileprocessor.deserializer;

import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FileDeserializerFactory {
    public final CsvDeserializer csvDeserializer;
    public final JsonDeserializer jsonDeserializer;
    public final XmlDeserializer xmlDeserializer;

    public FileDeserializer getDeserializer(FileMetadata.FileType fileType) {
        return switch (fileType) {
            case CSV -> csvDeserializer;
            case JSON -> jsonDeserializer;
            case XML -> xmlDeserializer;
        };
    }
}
