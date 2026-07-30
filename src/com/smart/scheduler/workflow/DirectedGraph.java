package com.smart.scheduler.workflow;

import java.util.*;

public class DirectedGraph<T> {
    private final Map<T, Set<T>> adjacencyMap;

    public DirectedGraph() {
        this.adjacencyMap = new HashMap<>();
    }

    public void addVertex(T vertex) {
        adjacencyMap.putIfAbsent(vertex, new HashSet<>());
    }

    public void addEdge(T source, T destination) {
        addVertex(source);
        addVertex(destination);
        adjacencyMap.get(source).add(destination);
    }

    public boolean removeEdge(T source, T destination) {
        if (adjacencyMap.containsKey(source)) {
            return adjacencyMap.get(source).remove(destination);
        }
        return false;
    }

    public Set<T> getNeighbors(T vertex) {
        return adjacencyMap.getOrDefault(vertex, Collections.emptySet());
    }

    public Set<T> getAllVertices() {
        return adjacencyMap.keySet();
    }

    public Map<T, Integer> getInDegrees() {
        Map<T, Integer> inDegrees = new HashMap<>();
        for (T vertex : adjacencyMap.keySet()) {
            inDegrees.putIfAbsent(vertex, 0);
            for (T neighbor : adjacencyMap.get(vertex)) {
                inDegrees.put(neighbor, inDegrees.getOrDefault(neighbor, 0) + 1);
            }
        }
        return inDegrees;
    }

    public List<T> bfs(T startVertex) {
        List<T> result = new ArrayList<>();
        if (!adjacencyMap.containsKey(startVertex)) {
            return result;
        }

        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();

        queue.add(startVertex);
        visited.add(startVertex);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            result.add(current);

            for (T neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return result;
    }

    public List<T> dfs(T startVertex) {
        List<T> result = new ArrayList<>();
        if (!adjacencyMap.containsKey(startVertex)) {
            return result;
        }

        Set<T> visited = new HashSet<>();
        dfsRecursive(startVertex, visited, result);
        return result;
    }

    private void dfsRecursive(T current, Set<T> visited, List<T> result) {
        visited.add(current);
        result.add(current);

        for (T neighbor : getNeighbors(current)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, result);
            }
        }
    }
}
