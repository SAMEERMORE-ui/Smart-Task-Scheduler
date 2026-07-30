package com.smart.scheduler.workflow;

import com.smart.scheduler.task.Task;

public class WorkflowStep {
    private final String stepId;
    private final Task task;
    private boolean executed;

    public WorkflowStep(String stepId, Task task) {
        this.stepId = stepId;
        this.task = task;
        this.executed = false;
    }

    public String getStepId() {
        return stepId;
    }

    public Task getTask() {
        return task;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
