package com.smart.scheduler.command;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskService;

public class CreateTaskCommand implements Command {
    private final Task task;
    private final TaskService taskService;

    public CreateTaskCommand(Task task) {
        this.task = task;
        this.taskService = TaskService.getInstance();
    }

    @Override
    public void execute() {
        taskService.createTask(task);
    }

    @Override
    public void undo() {
        taskService.deleteTask(task.getId());
    }

    @Override
    public String getDescription() {
        return "Create Task: " + task.getTitle() + " (" + task.getId() + ")";
    }
}
