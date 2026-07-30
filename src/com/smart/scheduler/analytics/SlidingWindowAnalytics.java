package com.smart.scheduler.analytics;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public class SlidingWindowAnalytics {

    public double calculateCompletionRateInWindow(List<Task> tasks, int windowDays) {
        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }

        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(windowDays);

        long totalInWindow = 0;
        long completedInWindow = 0;

        for (Task task : tasks) {
            if (task.getCreatedAt() != null && task.getCreatedAt().isAfter(cutoffTime)) {
                totalInWindow++;
                if (task.getStatus() == TaskStatus.COMPLETED) {
                    completedInWindow++;
                }
            }
        }

        if (totalInWindow == 0) {
            return 0.0;
        }

        return ((double) completedInWindow / totalInWindow) * 100.0;
    }
}
