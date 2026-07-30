package com.smart.scheduler.dsa;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskPriority;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueManager {
    private final PriorityQueue<Task> priorityQueue;

    public PriorityQueueManager() {
        Comparator<Task> comparator = (t1, t2) -> {
            int p1 = t1.getPriority().getWeight();
            int p2 = t2.getPriority().getWeight();
            if (p1 != p2) {
                return Integer.compare(p2, p1);
            }
            if (t1.getDueDate() != null && t2.getDueDate() != null) {
                return t1.getDueDate().compareTo(t2.getDueDate());
            }
            return 0;
        };
        this.priorityQueue = new PriorityQueue<>(comparator);
    }

    public void addTask(Task task) {
        priorityQueue.offer(task);
    }

    public Task pollTask() {
        return priorityQueue.poll();
    }

    public Task peekTask() {
        return priorityQueue.peek();
    }

    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    public int size() {
        return priorityQueue.size();
    }
}
