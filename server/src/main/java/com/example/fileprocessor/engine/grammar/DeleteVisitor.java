package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.grammar.gen.FileQueryBaseVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DeleteVisitor extends FileQueryBaseVisitor<List<Map<String, Object>>> {
    private final List<Map<String, Object>> data;
    private final FileMetadata.FileType fileType;

    @Override
    public List<Map<String, Object>> visitDeleteStatement(FileQueryParser.DeleteStatementContext ctx) {
        return data.stream().filter(row -> {
            EvalVisitor evalVisitor = new EvalVisitor(row, fileType);
            return !Boolean.parseBoolean(evalVisitor.visit(ctx.comparison()).toString());
        }).toList();
    }
}
