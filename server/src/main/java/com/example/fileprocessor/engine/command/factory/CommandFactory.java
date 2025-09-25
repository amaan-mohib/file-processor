package com.example.fileprocessor.engine.command.factory;

import com.example.fileprocessor.engine.command.QueryCommand;

public interface CommandFactory {
    QueryCommand create(String args);
}
