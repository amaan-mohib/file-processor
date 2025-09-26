package com.example.fileprocessor.engine.grammar.setCommand;

import com.example.fileprocessor.engine.grammar.setCommand.gen.SetCommandBaseVisitor;
import com.example.fileprocessor.engine.grammar.setCommand.gen.SetCommandParser;

import java.util.Map;
import java.util.function.Function;

public class UpdateEvaluator extends SetCommandBaseVisitor<Function<Map<String, Object>, Void>> {
    @Override
    public Function<Map<String, Object>, Void> visitSetStatement(SetCommandParser.SetStatementContext ctx) {
        return row -> {
            EvalVisitor evalVisitor = new EvalVisitor(row);
            Object value = evalVisitor.visit(ctx.expression());
            if (ctx.target() instanceof SetCommandParser.TargetIdentifierContext) {
                String key = ctx.target().getText();
                row.put(key, value);
            }
            // implement TargetPath
            return null;
        };
    }
}
