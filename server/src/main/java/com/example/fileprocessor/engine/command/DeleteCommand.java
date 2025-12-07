package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.grammar.DeleteVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DeleteCommand implements QueryCommand {
    private final FileQueryParser.DeleteStatementContext ctx;

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data, List<String> headers, FileMetadata.FileType fileType) {
        return new DeleteVisitor(data, fileType).visitDeleteStatement(ctx);
    }
}
