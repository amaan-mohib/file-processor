package com.example.fileprocessor.deserializer;

import com.example.fileprocessor.util.GenericUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvDeserializer implements FileDeserializer {
    @Override
    public FileDeserializerOutput deserialize(InputStream inputStream) throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();
        List<String> headers;
        try (Reader reader = new InputStreamReader(inputStream)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).get();
            CSVParser parser = csvFormat.parse(reader);
            headers = parser.getHeaderNames();
            for (CSVRecord record : parser) {
                Map<String, Object> map = new HashMap<>();
                headers.forEach(header -> map.put(header, GenericUtil.inferType(record.get(header))));
                result.add(map);
            }
        }
        return new FileDeserializerOutput(result, headers);
    }
}
