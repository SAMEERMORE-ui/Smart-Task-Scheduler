package com.smart.scheduler.dsa;

import java.util.HashSet;
import java.util.Set;

public class CustomTrie {
    private final TrieNode root;

    public CustomTrie() {
        this.root = new TrieNode();
    }

    public void insert(String word, String taskId) {
        if (word == null || word.trim().isEmpty()) {
            return;
        }
        TrieNode current = root;
        String normalized = word.toLowerCase().trim();
        for (char ch : normalized.toCharArray()) {
            current = current.getChildren().computeIfAbsent(ch, c -> new TrieNode());
            current.addTaskId(taskId);
        }
        current.setEndOfWord(true);
    }

    public Set<String> searchPrefix(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            return new HashSet<>();
        }
        TrieNode current = root;
        String normalized = prefix.toLowerCase().trim();
        for (char ch : normalized.toCharArray()) {
            TrieNode node = current.getChildren().get(ch);
            if (node == null) {
                return new HashSet<>();
            }
            current = node;
        }
        return new HashSet<>(current.getTaskIds());
    }
}
