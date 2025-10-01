package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.grammar.InsertVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class InsertCommand implements QueryCommand {
    private final FileQueryParser.InsertStatementContext ctx;

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data, List<String> headers) {
        new InsertVisitor(data, headers).visitInsertStatement(ctx);
        return data;
    }
}
