package com.smart.scheduler.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DBTableStore {
    private static DBTableStore instance;

    private final Map<String, List<String>> userTable = new ConcurrentHashMap<>();
    private final Map<String, List<String>> taskTable = new ConcurrentHashMap<>();
    private final Map<String, List<String>> dependencyTable = new ConcurrentHashMap<>();
    private final Map<String, List<String>> notificationTable = new ConcurrentHashMap<>();

    private DBTableStore() {
    }

    public static synchronized DBTableStore getInstance() {
        if (instance == null) {
            instance = new DBTableStore();
        }
        return instance;
    }

    public Map<String, List<String>> getUserTable() {
        return userTable;
    }

    public Map<String, List<String>> getTaskTable() {
        return taskTable;
    }

    public Map<String, List<String>> getDependencyTable() {
        return dependencyTable;
    }

    public Map<String, List<String>> getNotificationTable() {
        return notificationTable;
    }
}
