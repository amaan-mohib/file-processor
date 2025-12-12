package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.RowProcessor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryBaseVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@AllArgsConstructor
public class SetVisitor extends FileQueryBaseVisitor<Void> {
    private final List<Map<String, Object>> data;
    private final FileMetadata.FileType fileType;

    @Override
    public Void visitSetStatement(FileQueryParser.SetStatementContext ctx) {
        RowProcessor.process(data.size(), index -> {
            Map<String, Object> row = data.get(index);
            processRow(row, ctx);
        });
        return null;
    }

    private void processRow(Map<String, Object> row, FileQueryParser.SetStatementContext ctx) {
        EvalVisitor evalVisitor = new EvalVisitor(row, fileType);
        for (var assignment : ctx.assignment()) {
            Object value = evalVisitor.visit(assignment.expression());
            var target = assignment.target();
            if (target instanceof FileQueryParser.TargetPathContext) {
                if(fileType.equals(FileMetadata.FileType.CSV)) {
                    throw new UnsupportedOperationException("Path traversal is not supported for CSV files.");
                }
                setPath(
                        row,
                        ((FileQueryParser.TargetPathContext) target).pathExpression(),
                        value
                );
            } else {
                String key = target.getText();
                row.put(key, value);
            }
        }
    }

    private void setPath(Map<String, Object> row, FileQueryParser.PathExpressionContext pathContext, Object value) {
        var target = new PathResolverVisitor(row).visitPathExpression(pathContext, true);
        if (target.parent instanceof Map && target.key != null) {
            ((Map<String, Object>) target.parent).put(target.key, value);
        }
        else if (target.parent instanceof List && target.index != null) {
            ((List<Object>) target.parent).set(target.index, value);
        }
        else {
            throw new RuntimeException("Invalid target for assignment");
        }
    }
}
