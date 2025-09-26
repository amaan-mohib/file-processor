package com.example.fileprocessor.engine.command.factory;

import com.example.fileprocessor.engine.command.QueryCommand;
import com.example.fileprocessor.engine.command.SetCommand;
import org.springframework.stereotype.Component;

@Component("SET")
public class SetCommandFactory implements CommandFactory {
    @Override
    public QueryCommand create(String args) {
        return new SetCommand("SET " + args);
    }
}
