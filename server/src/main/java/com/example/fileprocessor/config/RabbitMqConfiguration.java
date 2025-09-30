package com.example.fileprocessor.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    public static final String QUEUE_NAME = "fileProcessor";
    public static final String EXCHANGE_NAME = "jobProcess";
    public static final String ROUTING_KEY = "job";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false); // Durable: false for non-persistent queue
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
