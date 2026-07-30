package com.smart.scheduler.command;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskService;

public class DeleteTaskCommand implements Command {
    private final Task task;
    private final TaskService taskService;

    public DeleteTaskCommand(Task task) {
        this.task = task;
        this.taskService = TaskService.getInstance();
    }

    @Override
    public void execute() {
        taskService.deleteTask(task.getId());
    }

    @Override
    public void undo() {
        taskService.createTask(task);
    }

    @Override
    public String getDescription() {
        return "Delete Task: " + task.getTitle() + " (" + task.getId() + ")";
    }
}
