package com.smart.scheduler.search;

import com.smart.scheduler.task.Task;

import java.util.Comparator;
import java.util.List;

public class BinarySearcher {

    public static int searchTaskById(List<Task> sortedTasks, String targetId) {
        int low = 0;
        int high = sortedTasks.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Task midTask = sortedTasks.get(mid);
            int cmp = midTask.getId().compareTo(targetId);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
