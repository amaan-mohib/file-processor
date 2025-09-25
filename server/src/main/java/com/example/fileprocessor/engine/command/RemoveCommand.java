package com.example.fileprocessor.engine.command;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class RemoveCommand implements QueryCommand {
    private final String key;

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data) {
        data.forEach(map -> map.remove(key));
        return data;
    }
}
