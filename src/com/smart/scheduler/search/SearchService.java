package com.smart.scheduler.search;

import com.smart.scheduler.dsa.CustomTrie;
import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchService {
    private static SearchService instance;
    private final CustomTrie trie;

    private SearchService() {
        this.trie = new CustomTrie();
        reindexTrie();
    }

    public static synchronized SearchService getInstance() {
        if (instance == null) {
            instance = new SearchService();
        }
        return instance;
    }

    public void reindexTrie() {
        List<Task> tasks = TaskService.getInstance().getAllTasks();
        for (Task task : tasks) {
            for (String word : task.getTitle().split("\\s+")) {
                trie.insert(word, task.getId());
            }
            for (String tag : task.getTags()) {
                trie.insert(tag, task.getId());
            }
        }
    }

    public List<Task> searchTasksByKeywordKMP(String keyword) {
        List<Task> allTasks = TaskService.getInstance().getAllTasks();
        return allTasks.stream()
                .filter(t -> KMPSearcher.searchPattern(t.getTitle(), keyword) ||
                        KMPSearcher.searchPattern(t.getDescription(), keyword))
                .collect(Collectors.toList());
    }

    public List<Task> searchTasksByPrefixTrie(String prefix) {
        Set<String> matchedIds = trie.searchPrefix(prefix);
        return matchedIds.stream()
                .map(id -> TaskService.getInstance().getTask(id))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSortedByPriorityQuickSort() {
        List<Task> tasks = new ArrayList<>(TaskService.getInstance().getAllTasks());
        QuickSorter.sort(tasks, (t1, t2) -> Integer.compare(t2.getPriority().getWeight(), t1.getPriority().getWeight()));
        return tasks;
    }

    public List<Task> getTasksSortedByDurationMergeSort() {
        List<Task> tasks = new ArrayList<>(TaskService.getInstance().getAllTasks());
        MergeSorter.sort(tasks, Comparator.comparingInt(Task::getEstimatedDurationMinutes));
        return tasks;
    }
}
