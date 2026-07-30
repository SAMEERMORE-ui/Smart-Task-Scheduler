package com.smart.scheduler.ui;

public class MenuRenderer {

    public static void printHeader(String title) {
        System.out.println("\n=======================================================");
        System.out.println("  " + title.toUpperCase());
        System.out.println("=======================================================");
    }

    public static void printMainMenu() {
        printHeader("Smart Task Scheduler & Workflow Engine");
        System.out.println(" 1. User Authentication");
        System.out.println(" 2. Task Management");
        System.out.println(" 3. Workflow Engine & Dependency Graph");
        System.out.println(" 4. Task Scheduler & Background Worker");
        System.out.println(" 5. Priority Queue & Custom DSA Operations");
        System.out.println(" 6. Search & Sorting Module (KMP, Binary Search)");
        System.out.println(" 7. Analytics Dashboard (Sliding Window)");
        System.out.println(" 8. Export Reports (CSV, JSON, Text)");
        System.out.println(" 9. Activity Logs & System Logs");
        System.out.println("10. Undo / Redo Command History");
        System.out.println(" 0. Exit");
        System.out.println("-------------------------------------------------------");
    }

    public static void printTaskMenu() {
        printHeader("Task Management");
        System.out.println(" 1. Create New Task");
        System.out.println(" 2. View All Tasks");
        System.out.println(" 3. View Task Details by ID");
        System.out.println(" 4. Update Task Status");
        System.out.println(" 5. Delete Task");
        System.out.println(" 6. Add Task Dependency");
        System.out.println(" 0. Back to Main Menu");
        System.out.println("-------------------------------------------------------");
    }

    public static void printWorkflowMenu() {
        printHeader("Workflow Engine & Dependency Graph");
        System.out.println(" 1. View Dependency Graph Topological Order");
        System.out.println(" 2. Execute Sequential Workflow Process");
        System.out.println(" 3. Run BFS Graph Traversal");
        System.out.println(" 4. Run DFS Graph Traversal");
        System.out.println(" 0. Back to Main Menu");
        System.out.println("-------------------------------------------------------");
    }
}
