package com.smart.scheduler.reporting;

import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReportingService {
    private static ReportingService instance;

    private ReportingService() {
    }

    public static synchronized ReportingService getInstance() {
        if (instance == null) {
            instance = new ReportingService();
        }
        return instance;
    }

    public void generateAndSaveReport(ReportExporter exporter, String filename) throws IOException {
        List<Task> tasks = TaskService.getInstance().getAllTasks();
        String reportContent = exporter.exportReport(tasks);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(reportContent);
        }
    }
}
