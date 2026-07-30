package com.smart.scheduler.task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FileTaskRepository implements TaskRepository {
    private final String filePath;
    private final ConcurrentHashMap<String, Task> taskMap;

    public FileTaskRepository(String filePath) {
        this.filePath = filePath;
        this.taskMap = new ConcurrentHashMap<>();
        loadData();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Task> list = (List<Task>) ois.readObject();
            for (Task task : list) {
                taskMap.put(task.getId(), task);
            }
        } catch (Exception ignored) {
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(taskMap.values()));
        } catch (IOException ignored) {
        }
    }

    @Override
    public synchronized void save(Task task) {
        taskMap.put(task.getId(), task);
        saveData();
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(taskMap.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public synchronized boolean delete(String id) {
        boolean removed = taskMap.remove(id) != null;
        if (removed) {
            saveData();
        }
        return removed;
    }

    @Override
    public synchronized void update(Task task) {
        if (taskMap.containsKey(task.getId())) {
            taskMap.put(task.getId(), task);
            saveData();
        }
    }
}
