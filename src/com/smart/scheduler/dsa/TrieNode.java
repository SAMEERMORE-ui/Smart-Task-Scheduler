package com.smart.scheduler.dsa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TrieNode {
    private final Map<Character, TrieNode> children = new HashMap<>();
    private boolean isEndOfWord = false;
    private final Set<String> taskIds = new HashSet<>();

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public Set<String> getTaskIds() {
        return taskIds;
    }

    public void addTaskId(String taskId) {
        taskIds.add(taskId);
    }
}
