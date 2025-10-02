package com.example.fileprocessor.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Component
public class JsonSerializer implements FileSerializer {
    @Override
    public void serialize(List<Map<String, Object>> result, List<String> headers, Path path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            mapper.writeValue(writer, result);
        }
    }
}
