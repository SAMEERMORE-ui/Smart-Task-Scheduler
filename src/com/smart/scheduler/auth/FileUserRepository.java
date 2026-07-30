package com.smart.scheduler.auth;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FileUserRepository implements UserRepository {
    private final String filePath;
    private final ConcurrentHashMap<String, User> userMap;

    public FileUserRepository(String filePath) {
        this.filePath = filePath;
        this.userMap = new ConcurrentHashMap<>();
        loadData();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<User> list = (List<User>) ois.readObject();
            for (User user : list) {
                userMap.put(user.getId(), user);
            }
        } catch (Exception ignored) {
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(userMap.values()));
        } catch (IOException ignored) {
        }
    }

    @Override
    public synchronized void save(User user) {
        userMap.put(user.getId(), user);
        saveData();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userMap.values().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public synchronized boolean delete(String id) {
        boolean removed = userMap.remove(id) != null;
        if (removed) {
            saveData();
        }
        return removed;
    }
}
