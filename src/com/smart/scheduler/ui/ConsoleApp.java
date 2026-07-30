package com.smart.scheduler.ui;

import com.smart.scheduler.analytics.AnalyticsService;
import com.smart.scheduler.auth.AuthManager;
import com.smart.scheduler.auth.Role;
import com.smart.scheduler.auth.User;
import com.smart.scheduler.command.*;
import com.smart.scheduler.dsa.PriorityQueueManager;
import com.smart.scheduler.logger.CustomLogger;
import com.smart.scheduler.logger.LogEntry;
import com.smart.scheduler.reporting.*;
import com.smart.scheduler.scheduler.AutoSchedulerGreedy;
import com.smart.scheduler.scheduler.TaskSchedulerService;
import com.smart.scheduler.search.SearchService;
import com.smart.scheduler.task.*;
import com.smart.scheduler.workflow.DependencyManager;
import com.smart.scheduler.workflow.DirectedGraph;
import com.smart.scheduler.workflow.WorkflowEngine;
import com.smart.scheduler.workflow.WorkflowProcess;
import com.smart.scheduler.workflow.WorkflowStep;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApp {
    private final Scanner scanner;
    private final InputValidator inputValidator;
    private final AuthManager authManager;
    private final TaskService taskService;
    private final CommandHistory commandHistory;

    public ConsoleApp() {
        this.scanner = new Scanner(System.in);
        this.inputValidator = new InputValidator(scanner);
        this.authManager = AuthManager.getInstance();
        this.taskService = TaskService.getInstance();
        this.commandHistory = CommandHistory.getInstance();
        seedDemoData();
    }

    private void seedDemoData() {
        if (taskService.getAllTasks().isEmpty()) {
            Task t1 = new TaskBuilder()
                    .setId("TSK-101")
                    .setTitle("Setup Database Schema")
                    .setDescription("Design relational tables for application")
                    .setPriority(TaskPriority.HIGH)
                    .setEstimatedDurationMinutes(45)
                    .build();

            Task t2 = new TaskBuilder()
                    .setId("TSK-102")
                    .setTitle("Build Auth Controller")
                    .setDescription("Implement login and user registration")
                    .setPriority(TaskPriority.MEDIUM)
                    .setEstimatedDurationMinutes(30)
                    .addDependency("TSK-101")
                    .build();

            Task t3 = new TaskBuilder()
                    .setId("TSK-103")
                    .setTitle("Deploy Core Services")
                    .setDescription("Deploy application to cloud instance")
                    .setPriority(TaskPriority.CRITICAL)
                    .setEstimatedDurationMinutes(60)
                    .addDependency("TSK-102")
                    .build();

            taskService.createTask(t1);
            taskService.createTask(t2);
            taskService.createTask(t3);
        }
    }

    public void start() {
        boolean running = true;
        while (running) {
            MenuRenderer.printMainMenu();
            int choice = inputValidator.readInt("Select an option: ", 0, 10);
            switch (choice) {
                case 1 -> handleAuth();
                case 2 -> handleTaskManagement();
                case 3 -> handleWorkflowEngine();
                case 4 -> handleScheduler();
                case 5 -> handlePriorityQueueDSA();
                case 6 -> handleSearchAndSorting();
                case 7 -> handleAnalytics();
                case 8 -> handleReporting();
                case 9 -> handleLogs();
                case 10 -> handleCommandHistory();
                case 0 -> {
                    System.out.println("Shutting down Task Scheduler Engine... Goodbye!");
                    TaskSchedulerService.getInstance().shutdown();
                    running = false;
                }
            }
        }
    }

    private void handleAuth() {
        MenuRenderer.printHeader("Authentication Menu");
        System.out.println("1. Login");
        System.out.println("2. Register New User");
        System.out.println("3. Current Session Info");
        System.out.println("4. Logout");
        int choice = inputValidator.readInt("Select option: ", 1, 4);
        try {
            if (choice == 1) {
                String username = inputValidator.readNonEmptyString("Username: ");
                String password = inputValidator.readNonEmptyString("Password: ");
                User user = authManager.login(username, password);
                System.out.println("Login successful! Welcome " + user.getUsername() + " [" + user.getRole() + "]");
            } else if (choice == 2) {
                String username = inputValidator.readNonEmptyString("Username: ");
                String password = inputValidator.readNonEmptyString("Password: ");
                String email = inputValidator.readNonEmptyString("Email: ");
                User user = authManager.register(username, password, email, Role.USER);
                System.out.println("Registration successful! Created user ID: " + user.getId());
            } else if (choice == 3) {
                if (authManager.isAuthenticated()) {
                    System.out.println("Logged in as: " + authManager.getCurrentUser().getUsername());
                } else {
                    System.out.println("No active user session.");
                }
            } else if (choice == 4) {
                authManager.logout();
                System.out.println("Logged out successfully.");
            }
        } catch (Exception e) {
            System.out.println("Auth Error: " + e.getMessage());
        }
    }

    private void handleTaskManagement() {
        boolean back = false;
        while (!back) {
            MenuRenderer.printTaskMenu();
            int choice = inputValidator.readInt("Select option: ", 0, 6);
            switch (choice) {
                case 1 -> {
                    String title = inputValidator.readNonEmptyString("Task Title: ");
                    String desc = inputValidator.readNonEmptyString("Task Description: ");
                    System.out.println("Priority (1=LOW, 2=MEDIUM, 3=HIGH, 4=CRITICAL): ");
                    int pChoice = inputValidator.readInt("Choice: ", 1, 4);
                    TaskPriority priority = TaskPriority.values()[pChoice - 1];
                    int duration = inputValidator.readInt("Estimated Duration (mins): ", 5, 600);
                    Task task = new TaskBuilder()
                            .setTitle(title)
                            .setDescription(desc)
                            .setPriority(priority)
                            .setEstimatedDurationMinutes(duration)
                            .build();

                    Command cmd = new CreateTaskCommand(task);
                    commandHistory.executeCommand(cmd);
                    System.out.println("Task created successfully!");
                }
                case 2 -> {
                    List<Task> tasks = taskService.getAllTasks();
                    System.out.println("\n--- All Tasks (" + tasks.size() + ") ---");
                    for (Task t : tasks) {
                        System.out.println(t);
                    }
                }
                case 3 -> {
                    String id = inputValidator.readNonEmptyString("Enter Task ID: ");
                    try {
                        Task task = taskService.getTask(id);
                        System.out.println(task);
                        System.out.println("Dependencies: " + task.getDependencies());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 4 -> {
                    String id = inputValidator.readNonEmptyString("Enter Task ID: ");
                    System.out.println("Select Status (1=PENDING, 2=IN_PROGRESS, 3=COMPLETED, 4=FAILED, 5=CANCELLED): ");
                    int sChoice = inputValidator.readInt("Choice: ", 1, 5);
                    TaskStatus status = TaskStatus.values()[sChoice - 1];
                    try {
                        Task task = taskService.getTask(id);
                        Command cmd = new UpdateStatusCommand(id, task.getStatus(), status);
                        commandHistory.executeCommand(cmd);
                        System.out.println("Status updated!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 5 -> {
                    String id = inputValidator.readNonEmptyString("Enter Task ID: ");
                    try {
                        Task task = taskService.getTask(id);
                        Command cmd = new DeleteTaskCommand(task);
                        commandHistory.executeCommand(cmd);
                        System.out.println("Task deleted!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 6 -> {
                    String id = inputValidator.readNonEmptyString("Enter Task ID: ");
                    String depId = inputValidator.readNonEmptyString("Enter Dependency Task ID: ");
                    try {
                        Task task = taskService.getTask(id);
                        task.addDependency(depId);
                        taskService.updateTask(task);
                        System.out.println("Added dependency " + depId + " to task " + id);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 0 -> back = true;
            }
        }
    }

    private void handleWorkflowEngine() {
        boolean back = false;
        while (!back) {
            MenuRenderer.printWorkflowMenu();
            int choice = inputValidator.readInt("Select option: ", 0, 4);
            switch (choice) {
                case 1 -> {
                    try {
                        List<String> order = DependencyManager.getInstance().getExecutionOrder();
                        System.out.println("\nTopological Execution Order: " + order);
                    } catch (Exception e) {
                        System.out.println("Workflow Error: " + e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        List<String> order = DependencyManager.getInstance().getExecutionOrder();
                        WorkflowProcess process = new WorkflowProcess("WF-1", "Standard Pipeline");
                        for (String taskId : order) {
                            Task t = taskService.getTask(taskId);
                            process.addStep(new WorkflowStep("STEP-" + taskId, t));
                        }
                        System.out.println("Executing workflow with " + process.getSteps().size() + " steps...");
                        WorkflowEngine.getInstance().runWorkflow(process);
                        System.out.println("Workflow process executed successfully!");
                    } catch (Exception e) {
                        System.out.println("Workflow Execution Failed: " + e.getMessage());
                    }
                }
                case 3 -> {
                    DirectedGraph<String> graph = DependencyManager.getInstance().buildDependencyGraph();
                    if (!graph.getAllVertices().isEmpty()) {
                        String start = graph.getAllVertices().iterator().next();
                        System.out.println("BFS Traversal starting from " + start + ": " + graph.bfs(start));
                    }
                }
                case 4 -> {
                    DirectedGraph<String> graph = DependencyManager.getInstance().buildDependencyGraph();
                    if (!graph.getAllVertices().isEmpty()) {
                        String start = graph.getAllVertices().iterator().next();
                        System.out.println("DFS Traversal starting from " + start + ": " + graph.dfs(start));
                    }
                }
                case 0 -> back = true;
            }
        }
    }

    private void handleScheduler() {
        MenuRenderer.printHeader("Task Scheduler & Worker Engine");
        System.out.println("1. Schedule Task Delayed Execution (Seconds)");
        System.out.println("2. Auto-Schedule Tasks (Greedy Optimization Algorithm)");
        int choice = inputValidator.readInt("Choice: ", 1, 2);
        if (choice == 1) {
            String id = inputValidator.readNonEmptyString("Enter Task ID: ");
            int seconds = inputValidator.readInt("Delay in Seconds: ", 1, 60);
            TaskSchedulerService.getInstance().scheduleTask(id, seconds);
            System.out.println("Task scheduled for background execution!");
        } else if (choice == 2) {
            List<Task> tasks = taskService.getAllTasks();
            AutoSchedulerGreedy optimizer = new AutoSchedulerGreedy();
            List<Task> optimized = optimizer.optimizeSchedule(tasks);
            System.out.println("\n--- Optimized Schedule (Greedy Algorithm) ---");
            for (Task t : optimized) {
                System.out.println(t);
            }
        }
    }

    private void handlePriorityQueueDSA() {
        MenuRenderer.printHeader("Priority Queue Operations");
        PriorityQueueManager pqManager = new PriorityQueueManager();
        for (Task t : taskService.getAllTasks()) {
            pqManager.addTask(t);
        }
        System.out.println("Polling tasks in Priority Order:");
        while (!pqManager.isEmpty()) {
            Task t = pqManager.pollTask();
            System.out.println(" -> Priority: " + t.getPriority() + " | Task: " + t.getTitle());
        }
    }

    private void handleSearchAndSorting() {
        MenuRenderer.printHeader("Search & Sorting Algorithms");
        System.out.println("1. KMP String Search (Task Title/Description)");
        System.out.println("2. QuickSort Tasks by Priority");
        System.out.println("3. MergeSort Tasks by Estimated Duration");
        int choice = inputValidator.readInt("Choice: ", 1, 3);
        if (choice == 1) {
            String keyword = inputValidator.readNonEmptyString("Enter Keyword to Search: ");
            List<Task> results = SearchService.getInstance().searchTasksByKeywordKMP(keyword);
            System.out.println("Found " + results.size() + " matches:");
            results.forEach(t -> System.out.println(t));
        } else if (choice == 2) {
            List<Task> sorted = SearchService.getInstance().getTasksSortedByPriorityQuickSort();
            System.out.println("\nTasks Sorted by QuickSort (Priority):");
            sorted.forEach(t -> System.out.println(t));
        } else if (choice == 3) {
            List<Task> sorted = SearchService.getInstance().getTasksSortedByDurationMergeSort();
            System.out.println("\nTasks Sorted by MergeSort (Duration):");
            sorted.forEach(t -> System.out.println(t));
        }
    }

    private void handleAnalytics() {
        MenuRenderer.printHeader("Analytics Dashboard");
        AnalyticsService analytics = AnalyticsService.getInstance();
        System.out.printf("Weekly Completion Rate (Sliding Window 7 Days): %.2f%%\n", analytics.getWeeklyCompletionRate());
        System.out.printf("Monthly Completion Rate (Sliding Window 30 Days): %.2f%%\n", analytics.getMonthlyCompletionRate());
        System.out.println("Total Estimated Workload (Pending Tasks): " + analytics.getTotalEstimatedWorkloadMinutes() + " mins");
        System.out.println("\nTask Count by Status: " + analytics.getTaskCountByStatus());
        System.out.println("Task Count by Priority: " + analytics.getTaskCountByPriority());
    }

    private void handleReporting() {
        MenuRenderer.printHeader("Export Reports");
        System.out.println("1. Export CSV Report");
        System.out.println("2. Export JSON Report");
        System.out.println("3. Export Formatted Text Report");
        int choice = inputValidator.readInt("Choice: ", 1, 3);
        try {
            if (choice == 1) {
                ReportingService.getInstance().generateAndSaveReport(new CSVReportExporter(), "tasks_report.csv");
                System.out.println("Exported tasks_report.csv successfully!");
            } else if (choice == 2) {
                ReportingService.getInstance().generateAndSaveReport(new JSONReportExporter(), "tasks_report.json");
                System.out.println("Exported tasks_report.json successfully!");
            } else if (choice == 3) {
                ReportingService.getInstance().generateAndSaveReport(new TextReportExporter(), "tasks_report.txt");
                System.out.println("Exported tasks_report.txt successfully!");
            }
        } catch (Exception e) {
            System.out.println("Report Error: " + e.getMessage());
        }
    }

    private void handleLogs() {
        MenuRenderer.printHeader("System Activity Logs");
        List<LogEntry> logs = CustomLogger.getInstance().getLogs();
        System.out.println("Total Log Entries: " + logs.size());
        for (LogEntry entry : logs) {
            System.out.println(entry);
        }
    }

    private void handleCommandHistory() {
        MenuRenderer.printHeader("Undo / Redo Command History");
        System.out.println("1. Undo Last Action");
        System.out.println("2. Redo Last Action");
        int choice = inputValidator.readInt("Choice: ", 1, 2);
        if (choice == 1) {
            if (commandHistory.undo()) {
                System.out.println("Undo completed successfully!");
            } else {
                System.out.println("Nothing to undo.");
            }
        } else {
            if (commandHistory.redo()) {
                System.out.println("Redo completed successfully!");
            } else {
                System.out.println("Nothing to redo.");
            }
        }
    }
}
