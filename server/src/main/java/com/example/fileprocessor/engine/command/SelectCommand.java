package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.grammar.SelectVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SelectCommand implements QueryCommand {
    private final FileQueryParser.SelectStatementContext ctx;

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data, List<String> headers) {
        return new SelectVisitor(data, headers).visitSelectStatement(ctx);
    }
}
