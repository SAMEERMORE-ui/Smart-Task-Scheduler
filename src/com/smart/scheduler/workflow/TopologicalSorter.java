package com.smart.scheduler.workflow;

import com.smart.scheduler.exception.DependencyCycleException;

import java.util.*;

public class TopologicalSorter<T> {

    public List<T> sort(DirectedGraph<T> graph) {
        Map<T, Integer> inDegree = graph.getInDegrees();
        Queue<T> queue = new LinkedList<>();

        for (Map.Entry<T, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<T> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            T current = queue.poll();
            result.add(current);

            for (T neighbor : graph.getNeighbors(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != graph.getAllVertices().size()) {
            throw new DependencyCycleException("Circular dependency detected in graph process!");
        }

        return result;
    }
}
