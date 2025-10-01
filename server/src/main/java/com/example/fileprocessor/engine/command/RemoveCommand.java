package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.grammar.RemoveVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class RemoveCommand implements QueryCommand {
    private final FileQueryParser.RemoveStatementContext ctx;

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data, List<String> headers) {
        new RemoveVisitor(data).visitRemoveStatement(ctx);
        return data;
    }
}
