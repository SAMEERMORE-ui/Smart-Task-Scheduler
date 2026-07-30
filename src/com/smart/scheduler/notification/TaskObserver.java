package com.smart.scheduler.notification;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskStatus;

public interface TaskObserver {
    void onTaskCreated(Task task);
    void onTaskStatusChanged(Task task, TaskStatus oldStatus, TaskStatus newStatus);
}
