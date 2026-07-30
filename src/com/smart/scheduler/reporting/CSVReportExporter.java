package com.smart.scheduler.reporting;

import com.smart.scheduler.task.Task;

import java.util.List;

public class CSVReportExporter implements ReportExporter {
    @Override
    public String exportReport(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Title,Priority,Status,Category,DueDate,DurationMinutes\n");
        for (Task t : tasks) {
            sb.append(t.getId()).append(",")
              .append("\"").append(t.getTitle().replace("\"", "\"\"")).append("\",")
              .append(t.getPriority()).append(",")
              .append(t.getStatus()).append(",")
              .append(t.getCategoryId()).append(",")
              .append(t.getDueDate() != null ? t.getDueDate().toString() : "").append(",")
              .append(t.getEstimatedDurationMinutes()).append("\n");
        }
        return sb.toString();
    }
}
