package com.smart.scheduler.command;

public interface Command {
    void execute();
    void undo();
    String getDescription();
}
