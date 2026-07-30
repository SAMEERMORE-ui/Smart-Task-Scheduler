package com.smart.scheduler.scheduler;

import com.smart.scheduler.task.Task;

import java.util.ArrayList;
import java.util.List;

public class AutoSchedulerGreedy {

    public List<Task> optimizeSchedule(List<Task> tasks) {
        List<Task> sortedTasks = new ArrayList<>(tasks);
        sortedTasks.sort((t1, t2) -> {
            int p1 = t1.getPriority().getWeight();
            int p2 = t2.getPriority().getWeight();
            if (p1 != p2) {
                return Integer.compare(p2, p1);
            }
            if (t1.getDueDate() != null && t2.getDueDate() != null) {
                return t1.getDueDate().compareTo(t2.getDueDate());
            }
            return Integer.compare(t1.getEstimatedDurationMinutes(), t2.getEstimatedDurationMinutes());
        });
        return sortedTasks;
    }
}
