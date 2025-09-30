package com.example.fileprocessor.queue;

import com.example.fileprocessor.config.RabbitMqConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME, RabbitMqConfiguration.ROUTING_KEY, message);
        System.out.println("Message sent: " + message);
    }
}
