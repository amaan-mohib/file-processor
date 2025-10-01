package com.example.fileprocessor.serializer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Component
public class CsvSerializer implements FileSerializer {
    @Override
    public void serialize(List<Map<String, Object>> result, List<String> headers, Path path) throws IOException {
        if (headers.isEmpty()) {
            throw new IllegalArgumentException("Headers must not be empty");
        }
        Set<String> headersSet = new HashSet<>(headers);
        List<String> rowHeaders = result.getFirst().keySet().stream().toList();
        Set<String> rowHeadersSet = new HashSet<>(rowHeaders);
        List<String> newHeader = headersSet.equals(rowHeadersSet) ? headers : rowHeaders;
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            CSVPrinter csvPrinter = new CSVPrinter(
                    writer,
                    CSVFormat.DEFAULT.builder().setHeader(newHeader.toArray(new String[0])).get()
            );

            for (Map<String, Object> record : result) {
                List<Object> values = new ArrayList<>();
                newHeader.forEach(header -> values.add(record.get(header)));
                csvPrinter.printRecord(values);
            }
            csvPrinter.flush();
        }
    }
}
