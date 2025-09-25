package com.example.fileprocessor.engine.command;

import java.util.List;
import java.util.Map;

public interface QueryCommand {
    List<Map<String, Object>> execute(List<Map<String, Object>> data);
}
