package com.example.fileprocessor.util;

import com.example.fileprocessor.entity.FileMetadata;

public class GenericUtil {
    private GenericUtil() {
    }

    public static FileMetadata.FileType getFileType(String type) {
        if (type.equalsIgnoreCase("csv")) {
            return FileMetadata.FileType.CSV;
        }
        if (type.equalsIgnoreCase("json")) {
            return FileMetadata.FileType.JSON;
        }
        if (type.equalsIgnoreCase("xml")) {
            return FileMetadata.FileType.XML;
        }
        return FileMetadata.FileType.CSV;
    }

    public static Throwable getRootCause(Throwable throwable) {
        if (throwable.getCause() != null) {
            return getRootCause(throwable.getCause());
        }
        return throwable;
    }
}
