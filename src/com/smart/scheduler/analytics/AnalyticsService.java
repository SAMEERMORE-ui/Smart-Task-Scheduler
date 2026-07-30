package com.smart.scheduler.analytics;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskPriority;
import com.smart.scheduler.task.TaskStatus;
import com.smart.scheduler.task.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsService {
    private static AnalyticsService instance;
    private final SlidingWindowAnalytics slidingWindowAnalytics;

    private AnalyticsService() {
        this.slidingWindowAnalytics = new SlidingWindowAnalytics();
    }

    public static synchronized AnalyticsService getInstance() {
        if (instance == null) {
            instance = new AnalyticsService();
        }
        return instance;
    }

    public Map<TaskStatus, Long> getTaskCountByStatus() {
        List<Task> tasks = TaskService.getInstance().getAllTasks();
        Map<TaskStatus, Long> map = new HashMap<>();
        for (TaskStatus status : TaskStatus.values()) {
            map.put(status, 0L);
        }
        for (Task task : tasks) {
            map.put(task.getStatus(), map.get(task.getStatus()) + 1);
        }
        return map;
    }

    public Map<TaskPriority, Long> getTaskCountByPriority() {
        List<Task> tasks = TaskService.getInstance().getAllTasks();
        Map<TaskPriority, Long> map = new HashMap<>();
        for (TaskPriority priority : TaskPriority.values()) {
            map.put(priority, 0L);
        }
        for (Task task : tasks) {
            map.put(task.getPriority(), map.get(task.getPriority()) + 1);
        }
        return map;
    }

    public double getWeeklyCompletionRate() {
        List<Task> tasks = TaskService.getInstance().getAllTasks();
        return slidingWindowAnalytics.calculateCompletionRateInWindow(tasks, 7);
    }

    public double getMonthlyCompletionRate() {
        List<Task> tasks = TaskService.getInstance().getAllTasks();
        return slidingWindowAnalytics.calculateCompletionRateInWindow(tasks, 30);
    }

    public int getTotalEstimatedWorkloadMinutes() {
        return TaskService.getInstance().getAllTasks().stream()
                .filter(t -> t.getStatus() != TaskStatus.COMPLETED && t.getStatus() != TaskStatus.CANCELLED)
                .mapToInt(Task::getEstimatedDurationMinutes)
                .sum();
    }
}
