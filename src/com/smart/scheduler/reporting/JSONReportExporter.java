package com.smart.scheduler.reporting;

import com.smart.scheduler.task.Task;

import java.util.List;

public class JSONReportExporter implements ReportExporter {
    @Override
    public String exportReport(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            sb.append("  {\n");
            sb.append("    \"id\": \"").append(t.getId()).append("\",\n");
            sb.append("    \"title\": \"").append(t.getTitle()).append("\",\n");
            sb.append("    \"priority\": \"").append(t.getPriority()).append("\",\n");
            sb.append("    \"status\": \"").append(t.getStatus()).append("\",\n");
            sb.append("    \"estimatedDuration\": ").append(t.getEstimatedDurationMinutes()).append("\n");
            sb.append("  }");
            if (i < tasks.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]\n");
        return sb.toString();
    }
}
