package com.smart.scheduler.scheduler;

import com.smart.scheduler.logger.CustomLogger;
import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskStatus;
import com.smart.scheduler.task.TaskService;

public class TaskExecutionWorker implements Runnable {
    private final Task task;

    public TaskExecutionWorker(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        CustomLogger.getInstance().info("Worker starting execution for Task: " + task.getId() + " - " + task.getTitle(), "TaskExecutionWorker");
        TaskService.getInstance().updateTaskStatus(task.getId(), TaskStatus.IN_PROGRESS);
        try {
            int workTime = Math.min(task.getEstimatedDurationMinutes() * 10, 2000);
            Thread.sleep(Math.max(workTime, 500));
            TaskService.getInstance().updateTaskStatus(task.getId(), TaskStatus.COMPLETED);
            CustomLogger.getInstance().info("Worker finished Task: " + task.getId(), "TaskExecutionWorker");
        } catch (Exception e) {
            TaskService.getInstance().updateTaskStatus(task.getId(), TaskStatus.FAILED);
            CustomLogger.getInstance().error("Worker failed Task: " + task.getId() + " - " + e.getMessage(), "TaskExecutionWorker");
        }
    }
}
