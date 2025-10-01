package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.grammar.gen.FileQueryBaseVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.exception.SyntaxException;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@AllArgsConstructor
public class InsertVisitor extends FileQueryBaseVisitor<Void> {
    private final List<Map<String, Object>> data;
    private final List<String> headers;

    @Override
    public Void visitInsertStatement(FileQueryParser.InsertStatementContext ctx) throws SyntaxException {
        EvalVisitor evalVisitor = new EvalVisitor(headers);
        List<String> columns = evalVisitor.visitColumnList(ctx.columnList());
        List<Object> values = ctx.valueList().value().stream().map(evalVisitor::visitValue).toList();
        if (columns.size() != values.size()) {
            throw new SyntaxException("The number of columns and values do not match");
        }
        Map<String, Object> row = new HashMap<>();
        IntStream.range(0, columns.size()).forEach(index -> {
            row.put(columns.get(index), values.get(index));
        });
        data.add(row);
        System.out.println("row: "+row);
        return null;
    }
}
