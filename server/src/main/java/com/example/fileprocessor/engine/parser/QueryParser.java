package com.example.fileprocessor.engine.parser;

import com.example.fileprocessor.engine.command.CommandRegistry;
import com.example.fileprocessor.engine.command.QueryCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class QueryParser {
    private final CommandRegistry commandRegistry;

    public List<QueryCommand> parse(String query) {
        List<QueryCommand> commands = new ArrayList<>();
        String[] statements = query.split(";");
        for (String statement : statements) {
            if (statement.trim().isEmpty()) continue;
            commands.add(commandRegistry.getCommand(statement));
        }
        return commands;
    }
}
