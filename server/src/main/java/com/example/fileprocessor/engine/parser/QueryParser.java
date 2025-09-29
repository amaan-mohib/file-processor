package com.example.fileprocessor.engine.parser;

import com.example.fileprocessor.engine.command.CommandRegistry;
import com.example.fileprocessor.engine.command.QueryCommand;
import com.example.fileprocessor.engine.grammar.gen.FileQueryLexer;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.exception.SyntaxException;
import com.example.fileprocessor.exception.ThrowingErrorListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public List<QueryCommand> parse(String query) throws SyntaxException {
        try {
            FileQueryLexer lexer = new FileQueryLexer(CharStreams.fromString(query));
            lexer.removeErrorListeners();
            lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
            FileQueryParser parser = new FileQueryParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(ThrowingErrorListener.INSTANCE);

            List<QueryCommand> commands = new ArrayList<>();
            for (var statement : parser.query().statement()) {
                QueryCommand command = toCommand(statement);
                if (command == null) {
                    throw new SyntaxException("Syntax error: Command not found");
                }
                commands.add(command);
            }
            return commands;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SyntaxException("Syntax error: " + e.getMessage());
        }
    }
}
