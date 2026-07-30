package com.smart.scheduler.notification;

import java.time.LocalDateTime;

public class Notification {
    private final String id;
    private final NotificationType type;
    private final String message;
    private final LocalDateTime timestamp;

    public Notification(String id, NotificationType type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public NotificationType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + message + " (" + timestamp + ")";
    }
}
