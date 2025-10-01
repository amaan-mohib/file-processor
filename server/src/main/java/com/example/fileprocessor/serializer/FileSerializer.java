package com.example.fileprocessor.serializer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface FileSerializer {
    void serialize(List<Map<String, Object>> result, List<String> headers, Path path) throws IOException;
}
