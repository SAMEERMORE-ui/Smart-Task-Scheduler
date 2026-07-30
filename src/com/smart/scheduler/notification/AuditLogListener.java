package com.smart.scheduler.notification;

import com.smart.scheduler.logger.CustomLogger;
import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskStatus;

public class AuditLogListener implements TaskObserver {
    @Override
    public void onTaskCreated(Task task) {
        CustomLogger.getInstance().info("AUDIT: Task created - " + task.getId(), "AuditLogListener");
    }

    @Override
    public void onTaskStatusChanged(Task task, TaskStatus oldStatus, TaskStatus newStatus) {
        CustomLogger.getInstance().info("AUDIT: Task status transition - " + task.getId() + " from " + oldStatus + " to " + newStatus, "AuditLogListener");
    }
}
