package com.smart.scheduler.scheduler;

import java.time.LocalDateTime;

public class ScheduleConfig {
    private final String taskId;
    private final LocalDateTime initialExecutionTime;
    private final long repeatIntervalSeconds;
    private final boolean recurring;

    public ScheduleConfig(String taskId, LocalDateTime initialExecutionTime, long repeatIntervalSeconds, boolean recurring) {
        this.taskId = taskId;
        this.initialExecutionTime = initialExecutionTime;
        this.repeatIntervalSeconds = repeatIntervalSeconds;
        this.recurring = recurring;
    }

    public String getTaskId() {
        return taskId;
    }

    public LocalDateTime getInitialExecutionTime() {
        return initialExecutionTime;
    }

    public long getRepeatIntervalSeconds() {
        return repeatIntervalSeconds;
    }

    public boolean isRecurring() {
        return recurring;
    }
}
