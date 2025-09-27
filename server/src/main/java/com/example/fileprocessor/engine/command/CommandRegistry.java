package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import org.springframework.stereotype.Component;

@Component
public class CommandRegistry {
    public QueryCommand getCommand(FileQueryParser.SetStatementContext ctx) {
        return new SetCommand(ctx);
    }

    public QueryCommand getCommand(FileQueryParser.RemoveStatementContext ctx) {
        return new RemoveCommand(ctx);
    }
}
