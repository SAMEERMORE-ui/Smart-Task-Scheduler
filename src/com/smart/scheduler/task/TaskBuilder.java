package com.smart.scheduler.task;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TaskBuilder {
    private String id;
    private String title;
    private String description;
    private TaskPriority priority = TaskPriority.MEDIUM;
    private TaskStatus status = TaskStatus.PENDING;
    private String categoryId = "cat-1";
    private LocalDateTime dueDate;
    private boolean recurring = false;
    private long recurrenceIntervalMinutes = 0;
    private Set<String> dependencies = new HashSet<>();
    private Set<String> tags = new HashSet<>();
    private String assignedUserId;
    private int estimatedDurationMinutes = 30;

    public TaskBuilder() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public TaskBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public TaskBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder setPriority(TaskPriority priority) {
        this.priority = priority;
        return this;
    }

    public TaskBuilder setStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    public TaskBuilder setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public TaskBuilder setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public TaskBuilder setRecurring(boolean recurring, long intervalMinutes) {
        this.recurring = recurring;
        this.recurrenceIntervalMinutes = intervalMinutes;
        return this;
    }

    public TaskBuilder addDependency(String taskId) {
        this.dependencies.add(taskId);
        return this;
    }

    public TaskBuilder addTag(String tag) {
        this.tags.add(tag);
        return this;
    }

    public TaskBuilder setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
        return this;
    }

    public TaskBuilder setEstimatedDurationMinutes(int minutes) {
        this.estimatedDurationMinutes = minutes;
        return this;
    }

    public Task build() {
        Task task = new Task();
        task.setId(this.id);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setPriority(this.priority);
        task.setStatus(this.status);
        task.setCategoryId(this.categoryId);
        task.setDueDate(this.dueDate);
        task.setRecurring(this.recurring);
        task.setRecurrenceIntervalMinutes(this.recurrenceIntervalMinutes);
        task.setDependencies(this.dependencies);
        task.setTags(this.tags);
        task.setAssignedUserId(this.assignedUserId);
        task.setEstimatedDurationMinutes(this.estimatedDurationMinutes);
        return task;
    }
}
