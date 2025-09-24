package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.FileMetadata;
import com.example.fileprocessor.parser.FileParser;
import com.example.fileprocessor.parser.FileParserFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FileProcessingService {
    private final FileParserFactory fileParserFactory;

    public List<Map<String, Object>> processFile(FileMetadata file, InputStream inputStream) throws IOException {
        FileParser fileParser = fileParserFactory.getParser(file.getFileType());
        // TODO: execute query after parsing
        return fileParser.parse(inputStream);
    }
}
