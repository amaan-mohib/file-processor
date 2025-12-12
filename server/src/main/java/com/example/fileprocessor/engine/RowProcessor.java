package com.example.fileprocessor.engine;

import com.example.fileprocessor.exception.RowProcessingException;
import com.example.fileprocessor.payload.response.RowError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntConsumer;

@Service
public class RowProcessor {
    private static boolean multithreaded;

    @Value("${spring.application.multithreaded:false}")
    public void setMultithreaded(boolean multithreaded) {
        RowProcessor.multithreaded = multithreaded;
    }

    public static void parallelProcess(int rowIndex, IntConsumer processor) {
        int threads = Runtime.getRuntime().availableProcessors();
        List<RowError> errors;
        try (ExecutorService executorService = Executors.newFixedThreadPool(threads)) {
            List<Future<?>> futures = new ArrayList<>();
            errors = Collections.synchronizedList(new ArrayList<>());

            try {
                for (int i = 0; i < rowIndex; i++) {
                    final int currentIndex = i;
                    futures.add(executorService.submit(() -> {
                        try {
                            processor.accept(currentIndex);
                        } catch (Throwable e) {
                            errors.add(new RowError(currentIndex, e));
                        }
                    }));
                }

                for (Future<?> future : futures) {
                    future.get();
                }
            } catch (Exception e) {
                throw new RuntimeException("Error during parallel processing", e);
            } finally {
                executorService.shutdown();
            }
        }

        if (!errors.isEmpty()) {
            throw new RowProcessingException(errors);
        }
    }

    public static void sequentialProcess(int rowIndex, IntConsumer processor) {
        List<RowError> errors = new ArrayList<>();

        for (int i = 0; i < rowIndex; i++) {
            try {
                processor.accept(i);
            } catch (Throwable e) {
                errors.add(new RowError(i, e));
            }
        }

        if (!errors.isEmpty()) {
            throw new RowProcessingException(errors);
        }
    }

    public static void process(int rowIndex, IntConsumer processor) {
        System.out.println("RowProcessor: multithreaded = " + multithreaded);
        if (multithreaded) {
            parallelProcess(rowIndex, processor);
        } else {
            sequentialProcess(rowIndex, processor);
        }
    }
}
