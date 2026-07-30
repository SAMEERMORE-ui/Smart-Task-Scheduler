package com.smart.scheduler.command;

import com.smart.scheduler.task.TaskStatus;
import com.smart.scheduler.task.TaskService;

public class UpdateStatusCommand implements Command {
    private final String taskId;
    private final TaskStatus oldStatus;
    private final TaskStatus newStatus;
    private final TaskService taskService;

    public UpdateStatusCommand(String taskId, TaskStatus oldStatus, TaskStatus newStatus) {
        this.taskId = taskId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.taskService = TaskService.getInstance();
    }

    @Override
    public void execute() {
        taskService.updateTaskStatus(taskId, newStatus);
    }

    @Override
    public void undo() {
        taskService.updateTaskStatus(taskId, oldStatus);
    }

    @Override
    public String getDescription() {
        return "Update Status of " + taskId + ": " + oldStatus + " -> " + newStatus;
    }
}
