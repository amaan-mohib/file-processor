package com.example.fileprocessor.engine.command.factory;

import com.example.fileprocessor.engine.command.QueryCommand;
import com.example.fileprocessor.engine.command.RemoveCommand;
import org.springframework.stereotype.Component;

@Component("REMOVE")
public class RemoveCommandFactory implements CommandFactory {
    @Override
    public QueryCommand create(String args) {
        return new RemoveCommand(args.trim());
    }
}
