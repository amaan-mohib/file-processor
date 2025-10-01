package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.grammar.FilterVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class FilterCommand implements QueryCommand {
    private final FileQueryParser.FilterStatementContext ctx;

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data, List<String> headers) {
        return new FilterVisitor(data).visitFilterStatement(ctx);
    }
}
