package com.example.fileprocessor.util;

import com.example.fileprocessor.entity.FileMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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

    public static Object inferType(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException ignored) {}

        try {
            return Double.valueOf(value);
        } catch (NumberFormatException ignored) {}

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.valueOf(value);
        }

        return value;
    }

    public static String getObjectAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.writeValueAsString(obj);
    }
}
