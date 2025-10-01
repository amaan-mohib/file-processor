package com.example.fileprocessor.deserializer;

import java.io.IOException;
import java.io.InputStream;

public interface FileDeserializer {
    FileDeserializerOutput deserialize(InputStream inputStream) throws IOException;
}
