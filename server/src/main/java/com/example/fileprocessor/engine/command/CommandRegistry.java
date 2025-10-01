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

    public QueryCommand getCommand(FileQueryParser.InsertStatementContext ctx) {
        return new InsertCommand(ctx);
    }

    public QueryCommand getCommand(FileQueryParser.FilterStatementContext ctx) {
        return new FilterCommand(ctx);
    }

    public QueryCommand getCommand(FileQueryParser.DeleteStatementContext ctx) {
        return new DeleteCommand(ctx);
    }

    public QueryCommand getCommand(FileQueryParser.SelectStatementContext ctx) {
        return new SelectCommand(ctx);
    }
}
