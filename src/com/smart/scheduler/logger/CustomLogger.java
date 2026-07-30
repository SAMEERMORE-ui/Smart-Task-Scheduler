package com.smart.scheduler.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomLogger {
    private static CustomLogger instance;
    private final List<LogEntry> logs;
    private String logFilePath = "app_activity.log";
    private LogLevel minLevel = LogLevel.INFO;

    private CustomLogger() {
        this.logs = Collections.synchronizedList(new ArrayList<>());
    }

    public static synchronized CustomLogger getInstance() {
        if (instance == null) {
            instance = new CustomLogger();
        }
        return instance;
    }

    public void setLogFilePath(String path) {
        this.logFilePath = path;
    }

    public void setMinLevel(LogLevel level) {
        this.minLevel = level;
    }

    public synchronized void log(LogLevel level, String message, String source) {
        if (level.ordinal() < minLevel.ordinal()) {
            return;
        }
        LogEntry entry = new LogEntry(level, message, source);
        logs.add(entry);
        writeToFile(entry);
    }

    public void info(String message, String source) {
        log(LogLevel.INFO, message, source);
    }

    public void warn(String message, String source) {
        log(LogLevel.WARN, message, source);
    }

    public void error(String message, String source) {
        log(LogLevel.ERROR, message, source);
    }

    public void debug(String message, String source) {
        log(LogLevel.DEBUG, message, source);
    }

    private void writeToFile(LogEntry entry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            writer.println(entry.toString());
        } catch (IOException ignored) {
        }
    }

    public List<LogEntry> getLogs() {
        return new ArrayList<>(logs);
    }

    public void clearLogs() {
        logs.clear();
    }
}
