package com.example.fileprocessor.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface FileParser {
    List<Map<String, Object>> parse(InputStream inputStream) throws IOException;
}
