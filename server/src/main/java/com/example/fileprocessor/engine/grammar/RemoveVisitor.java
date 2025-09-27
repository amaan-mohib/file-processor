package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.grammar.gen.FileQueryBaseVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class RemoveVisitor extends FileQueryBaseVisitor<Void> {
    private final List<Map<String, Object>> data;

    @Override
    public Void visitRemoveStatement(FileQueryParser.RemoveStatementContext ctx) {
        for (Map<String, Object> row : data) {
            for (var target : ctx.target()) {
                String key = target.getText();
                row.remove(key);
            }
        }
        return null;
    }
}
