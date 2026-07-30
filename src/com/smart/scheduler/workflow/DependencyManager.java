package com.smart.scheduler.workflow;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskService;

import java.util.List;

public class DependencyManager {
    private static DependencyManager instance;
    private final TaskService taskService;

    private DependencyManager() {
        this.taskService = TaskService.getInstance();
    }

    public static synchronized DependencyManager getInstance() {
        if (instance == null) {
            instance = new DependencyManager();
        }
        return instance;
    }

    public DirectedGraph<String> buildDependencyGraph() {
        DirectedGraph<String> graph = new DirectedGraph<>();
        List<Task> tasks = taskService.getAllTasks();

        for (Task task : tasks) {
            graph.addVertex(task.getId());
        }

        for (Task task : tasks) {
            for (String depId : task.getDependencies()) {
                graph.addEdge(depId, task.getId());
            }
        }

        return graph;
    }

    public List<String> getExecutionOrder() {
        DirectedGraph<String> graph = buildDependencyGraph();
        TopologicalSorter<String> sorter = new TopologicalSorter<>();
        return sorter.sort(graph);
    }
}
