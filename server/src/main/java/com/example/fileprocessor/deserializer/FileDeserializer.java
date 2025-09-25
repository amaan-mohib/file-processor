package com.example.fileprocessor.deserializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface FileDeserializer {
    List<Map<String, Object>> deserialize(InputStream inputStream) throws IOException;
}
