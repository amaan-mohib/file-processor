package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.entity.FileMetadata;

import java.util.List;
import java.util.Map;

public interface QueryCommand {
    List<Map<String, Object>> execute(List<Map<String, Object>> data, List<String> headers, FileMetadata.FileType fileType);
}
