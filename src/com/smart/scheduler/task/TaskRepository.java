package com.smart.scheduler.task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    void save(Task task);
    Optional<Task> findById(String id);
    List<Task> findAll();
    boolean delete(String id);
    void update(Task task);
}
