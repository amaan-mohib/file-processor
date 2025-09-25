package com.example.fileprocessor.serializer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Component
public class CsvSerializer implements FileSerializer {
    @Override
    public void serialize(List<Map<String, Object>> result, Path path) throws IOException {
        if (result.isEmpty()) return;
        String[] headers = result.getFirst().keySet().toArray(new String[0]);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder().setHeader(headers).get());
            for (Map<String, Object> record : result) {
                csvPrinter.printRecord(record.values());
            }
            csvPrinter.flush();
        }
    }
}
