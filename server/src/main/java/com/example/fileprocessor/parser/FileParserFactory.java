package com.example.fileprocessor.parser;

import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@AllArgsConstructor
public class FileParserFactory {
    public CsvParser csvParser;

    public FileParser getParser(FileMetadata.FileType fileType) {
        return switch (fileType) {
            case CSV -> csvParser;
            // TODO: implement JSON and XML parser
            case JSON, XML -> throw new IllegalArgumentException("Unknown file type: " + fileType);
        };
    }
}
