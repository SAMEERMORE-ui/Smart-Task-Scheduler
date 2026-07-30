package com.smart.scheduler.reporting;

import com.smart.scheduler.task.Task;

import java.util.List;

public interface ReportExporter {
    String exportReport(List<Task> tasks);
}
