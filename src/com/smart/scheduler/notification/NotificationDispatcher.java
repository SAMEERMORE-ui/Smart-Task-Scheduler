package com.smart.scheduler.notification;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class NotificationDispatcher {
    private static NotificationDispatcher instance;
    private final List<TaskObserver> observers;

    private NotificationDispatcher() {
        this.observers = new ArrayList<>();
        registerObserver(new ConsoleNotificationListener());
        registerObserver(new AuditLogListener());
    }

    public static synchronized NotificationDispatcher getInstance() {
        if (instance == null) {
            instance = new NotificationDispatcher();
        }
        return instance;
    }

    public void registerObserver(TaskObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(TaskObserver observer) {
        observers.remove(observer);
    }

    public void notifyTaskCreated(Task task) {
        for (TaskObserver observer : observers) {
            observer.onTaskCreated(task);
        }
    }

    public void notifyTaskStatusChanged(Task task, TaskStatus oldStatus, TaskStatus newStatus) {
        for (TaskObserver observer : observers) {
            observer.onTaskStatusChanged(task, oldStatus, newStatus);
        }
    }
}
