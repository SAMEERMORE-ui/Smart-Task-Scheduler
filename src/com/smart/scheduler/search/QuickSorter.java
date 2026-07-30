package com.smart.scheduler.search;

import com.smart.scheduler.task.Task;

import java.util.Comparator;
import java.util.List;

public class QuickSorter {

    public static void sort(List<Task> list, Comparator<Task> comparator) {
        if (list == null || list.size() <= 1) {
            return;
        }
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private static void quickSort(List<Task> list, int low, int high, Comparator<Task> comparator) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);
            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }

    private static int partition(List<Task> list, int low, int high, Comparator<Task> comparator) {
        Task pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static void swap(List<Task> list, int i, int j) {
        Task temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
