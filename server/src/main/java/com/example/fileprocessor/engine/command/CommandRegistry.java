package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.command.factory.CommandFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class CommandRegistry {
    private final Map<String, CommandFactory> commandFactories;

    public QueryCommand getCommand(String commandStr) {
        String[] parts = commandStr.trim().split("\\s+", 2);
        String commandName = parts[0].toUpperCase();
        String args = parts.length > 1 ? parts[1] : "";

        CommandFactory factory = commandFactories.get(commandName);
        if (factory == null) {
            throw new IllegalArgumentException("Unknown command: " + commandName);
        }
        return factory.create(args);
    }
}
