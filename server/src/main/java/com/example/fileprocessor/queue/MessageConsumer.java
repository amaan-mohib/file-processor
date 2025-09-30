package com.example.fileprocessor.queue;

import com.example.fileprocessor.config.RabbitMqConfiguration;
import com.example.fileprocessor.service.JobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class MessageConsumer {
    private final JobService jobService;

    @RabbitListener(queues = RabbitMqConfiguration.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Message received: " + message);
        String[] parts = message.split(":", 2);
        String messageKey = parts[0];
        String messageArg = parts[1];
        if (messageKey.equals("job")) {
            try {
                jobService.runJob(UUID.fromString(messageArg), null);
            } catch (Exception e) {
                log.info("Error already logged for the job");
                log.error(e.getMessage());
            }
        }
    }
}
