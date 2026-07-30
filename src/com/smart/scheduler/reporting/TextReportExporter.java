package com.smart.scheduler.reporting;

import com.smart.scheduler.task.Task;

import java.util.List;

public class TextReportExporter implements ReportExporter {
    @Override
    public String exportReport(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("=====================================================\n");
        sb.append("               TASK SCHEDULER REPORT                 \n");
        sb.append("=====================================================\n");
        for (Task t : tasks) {
            sb.append(String.format("ID: %-10s | Priority: %-8s | Status: %-11s | Title: %s\n",
                    t.getId(), t.getPriority(), t.getStatus(), t.getTitle()));
        }
        sb.append("=====================================================\n");
        sb.append("Total Tasks Exported: ").append(tasks.size()).append("\n");
        return sb.toString();
    }
}
