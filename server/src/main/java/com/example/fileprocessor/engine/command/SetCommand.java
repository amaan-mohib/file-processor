package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.grammar.SetVisitor;
import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SetCommand implements QueryCommand {
    private final FileQueryParser.SetStatementContext ctx;

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data) {
        new SetVisitor(data).visitSetStatement(ctx);
        return data;
    }
}
