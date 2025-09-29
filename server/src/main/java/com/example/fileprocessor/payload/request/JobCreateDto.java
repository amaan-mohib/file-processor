package com.example.fileprocessor.payload.request;

import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobCreateDto {
    MultipartFile file;
    FileMetadata.FileType fileType;
}
