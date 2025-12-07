package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.grammar.gen.FileQueryBaseVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SelectVisitor extends FileQueryBaseVisitor<List<Map<String, Object>>> {
    private final List<Map<String, Object>> data;
    private final List<String> headers;
    private final FileMetadata.FileType fileType;

    @Override
    public List<Map<String, Object>> visitSelectStatement(FileQueryParser.SelectStatementContext ctx) {
        EvalVisitor evalVisitor = new EvalVisitor(headers, fileType);
        List<String> columns = evalVisitor.visitColumnList(ctx.columnList());
        List<Map<String, Object>> newData = new ArrayList<>();
        data.forEach(row -> {
            boolean toInclude = true;
            if (ctx.comparison() != null) {
                EvalVisitor evalRowVisitor = new EvalVisitor(row, fileType);
                toInclude = Boolean.parseBoolean(evalRowVisitor.visit(ctx.comparison()).toString());
            }
            if(toInclude) {
                Map<String, Object> newRow = new HashMap<>();
                columns.forEach(column -> newRow.put(column, row.get(column)));
                newData.add(newRow);
            }
        });
        return newData;
    }
}
