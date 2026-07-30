package com.smart.scheduler.workflow;

import java.util.List;

public interface WorkflowStrategy {
    void executeWorkflow(List<WorkflowStep> steps);
}
