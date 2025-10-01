package com.example.fileprocessor.engine;

import com.example.fileprocessor.engine.command.QueryCommand;
import com.example.fileprocessor.engine.parser.QueryParser;
import com.example.fileprocessor.exception.SyntaxException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class QueryEngine {
    private final QueryParser queryParser;

    public List<Map<String, Object>> execute(List<Map<String, Object>> data, List<String> headers, String query) throws SyntaxException {
        List<QueryCommand> commands = queryParser.parse(query);
        for (QueryCommand command : commands) {
            data = command.execute(data, headers);
        }
        return data;
    }
}
