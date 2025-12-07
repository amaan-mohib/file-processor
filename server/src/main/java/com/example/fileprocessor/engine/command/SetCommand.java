package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.grammar.SetVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import com.example.fileprocessor.entity.FileMetadata;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SetCommand implements QueryCommand {
    private final FileQueryParser.SetStatementContext ctx;

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data, List<String> headers, FileMetadata.FileType fileType) {
        new SetVisitor(data, fileType).visitSetStatement(ctx);
        return data;
    }
}
