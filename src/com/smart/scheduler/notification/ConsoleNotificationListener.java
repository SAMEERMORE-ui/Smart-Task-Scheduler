package com.smart.scheduler.notification;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskStatus;

public class ConsoleNotificationListener implements TaskObserver {
    @Override
    public void onTaskCreated(Task task) {
        System.out.println("[NOTIFICATION] New task created: " + task.getTitle() + " (ID: " + task.getId() + ")");
    }

    @Override
    public void onTaskStatusChanged(Task task, TaskStatus oldStatus, TaskStatus newStatus) {
        System.out.println("[NOTIFICATION] Task status changed [" + task.getId() + "]: " + oldStatus + " -> " + newStatus);
    }
}
