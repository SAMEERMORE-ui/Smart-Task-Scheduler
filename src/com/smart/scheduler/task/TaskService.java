package com.smart.scheduler.task;

import com.smart.scheduler.exception.TaskNotFoundException;
import com.smart.scheduler.logger.CustomLogger;
import com.smart.scheduler.notification.NotificationDispatcher;

import java.util.List;
import java.util.stream.Collectors;

public class TaskService {
    private static TaskService instance;
    private final TaskRepository taskRepository;

    private TaskService() {
        this.taskRepository = new FileTaskRepository("tasks.dat");
    }

    public static synchronized TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }
        return instance;
    }

    public void createTask(Task task) {
        taskRepository.save(task);
        CustomLogger.getInstance().info("Created task: " + task.getId() + " - " + task.getTitle(), "TaskService");
        NotificationDispatcher.getInstance().notifyTaskCreated(task);
    }

    public Task getTask(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void updateTask(Task task) {
        taskRepository.update(task);
        CustomLogger.getInstance().info("Updated task: " + task.getId(), "TaskService");
    }

    public void deleteTask(String id) {
        Task task = getTask(id);
        taskRepository.delete(id);
        CustomLogger.getInstance().info("Deleted task: " + id, "TaskService");
    }

    public void updateTaskStatus(String id, TaskStatus status) {
        Task task = getTask(id);
        TaskStatus oldStatus = task.getStatus();
        task.setStatus(status);
        taskRepository.update(task);
        CustomLogger.getInstance().info("Updated task status " + id + ": " + oldStatus + " -> " + status, "TaskService");
        NotificationDispatcher.getInstance().notifyTaskStatusChanged(task, oldStatus, status);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findAll().stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Task> getTasksByCategory(String categoryId) {
        return taskRepository.findAll().stream()
                .filter(t -> categoryId.equals(t.getCategoryId()))
                .collect(Collectors.toList());
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}
