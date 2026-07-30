package com.smart.scheduler.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CategoryManager {
    private static CategoryManager instance;
    private final Map<String, Category> categories;

    private CategoryManager() {
        this.categories = new ConcurrentHashMap<>();
        addDefaultCategories();
    }

    public static synchronized CategoryManager getInstance() {
        if (instance == null) {
            instance = new CategoryManager();
        }
        return instance;
    }

    private void addDefaultCategories() {
        addCategory(new Category("cat-1", "General", "Default category"));
        addCategory(new Category("cat-2", "Development", "Software engineering tasks"));
        addCategory(new Category("cat-3", "DevOps", "CI/CD and deployment tasks"));
        addCategory(new Category("cat-4", "Testing", "QA and automated test tasks"));
    }

    public void addCategory(Category category) {
        categories.put(category.getId(), category);
    }

    public Category getCategory(String id) {
        return categories.get(id);
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories.values());
    }
}
