package com.example.fileprocessor.engine.parser;

import com.example.fileprocessor.engine.command.CommandRegistry;
import com.example.fileprocessor.engine.command.QueryCommand;
import com.example.fileprocessor.engine.grammar.gen.FileQueryLexer;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.exception.SyntaxException;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class QueryParser {
    private final CommandRegistry commandRegistry;

    private QueryCommand toCommand(FileQueryParser.StatementContext ctx) {
        if (ctx.setStatement() != null) {
            return commandRegistry.getCommand(ctx.setStatement());
        }
        if (ctx.removeStatement() != null) {
            return commandRegistry.getCommand(ctx.removeStatement());
        }
        return null;
    }

    public List<QueryCommand> parse(String query) {
        FileQueryLexer lexer = new FileQueryLexer(CharStreams.fromString(query));
        FileQueryParser parser = new FileQueryParser(new CommonTokenStream(lexer));
        List<QueryCommand> commands = new ArrayList<>();
        for (var statement : parser.query().statement()) {
            QueryCommand command = toCommand(statement);
            if (command == null) {
                throw new SyntaxException("Syntax error: Command not found");
            }
            commands.add(command);
        }
        return commands;
    }
}
