package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.grammar.gen.FileQueryBaseVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DeleteVisitor extends FileQueryBaseVisitor<List<Map<String, Object>>> {
    private final List<Map<String, Object>> data;

    @Override
    public List<Map<String, Object>> visitDeleteStatement(FileQueryParser.DeleteStatementContext ctx) {
        return data.stream().filter(row -> {
            EvalVisitor evalVisitor = new EvalVisitor(row);
            return !Boolean.parseBoolean(evalVisitor.visit(ctx.comparison()).toString());
        }).toList();
    }
}
