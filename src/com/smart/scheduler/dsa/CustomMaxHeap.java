package com.smart.scheduler.dsa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomMaxHeap<T> {
    private final List<T> heap;
    private final Comparator<T> comparator;

    public CustomMaxHeap(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public void insert(T item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    public T poll() {
        if (heap.isEmpty()) {
            return null;
        }
        T max = heap.get(0);
        T lastItem = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastItem);
            siftDown(0);
        }
        return max;
    }

    public T peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parentIndex)) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int half = heap.size() / 2;
        while (index < half) {
            int left = 2 * index + 1;
            int right = left + 1;
            int largest = left;

            if (right < heap.size() && comparator.compare(heap.get(right), heap.get(left)) > 0) {
                largest = right;
            }

            if (comparator.compare(heap.get(largest), heap.get(index)) > 0) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
