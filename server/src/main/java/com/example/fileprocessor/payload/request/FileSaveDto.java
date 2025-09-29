package com.example.fileprocessor.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FileSaveDto {
    private String filePath;
    private UUID fileKey;
}
