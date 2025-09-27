package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.grammar.gen.FileQueryBaseVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.exception.SyntaxException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SetVisitor extends FileQueryBaseVisitor<Void> {
    private final List<Map<String, Object>> data;

    @Override
    public Void visitSetStatement(FileQueryParser.SetStatementContext ctx) {
        for (Map<String, Object> row : data) {
            EvalVisitor evalVisitor = new EvalVisitor(row);
            for (var assignment : ctx.assignment()) {
                String key = assignment.target().getText();
                Object value = evalVisitor.visit(assignment.expression());
                row.put(key, value);
            }
        }
        return null;
    }
}
