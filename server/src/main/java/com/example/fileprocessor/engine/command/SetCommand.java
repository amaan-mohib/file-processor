package com.example.fileprocessor.engine.command;

import com.example.fileprocessor.engine.grammar.setCommand.UpdateEvaluator;
import com.example.fileprocessor.engine.grammar.setCommand.gen.SetCommandLexer;
import com.example.fileprocessor.engine.grammar.setCommand.gen.SetCommandParser;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SetCommand implements QueryCommand {
    private final String query;

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data) {
        var lexer = new SetCommandLexer(CharStreams.fromString(query));
        var parser = new SetCommandParser(new CommonTokenStream(lexer));

        UpdateEvaluator updateEvaluator = new UpdateEvaluator();
        var updater = updateEvaluator.visit(parser.setStatement());
        data.forEach(updater::apply);
        return data;
    }
}
