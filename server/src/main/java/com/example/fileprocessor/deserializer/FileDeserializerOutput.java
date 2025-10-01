package com.example.fileprocessor.deserializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class FileDeserializerOutput {
    List<Map<String, Object>> data;
    List<String> headers;
}
