package com.example.fileprocessor.deserializer;

import com.example.fileprocessor.exception.SyntaxException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public class JsonDeserializer implements FileDeserializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public FileDeserializerOutput deserialize(InputStream inputStream) throws IOException {
        try {
            List<Map<String, Object>> result = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            return new FileDeserializerOutput(result, null);
        } catch (MismatchedInputException e) {
            throw new SyntaxException("JSON must be an array of objects.");
        }
    }
}
