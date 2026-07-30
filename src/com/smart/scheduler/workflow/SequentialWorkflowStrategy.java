package com.smart.scheduler.workflow;

import com.smart.scheduler.logger.CustomLogger;
import com.smart.scheduler.task.TaskStatus;
import com.smart.scheduler.task.TaskService;

import java.util.List;

public class SequentialWorkflowStrategy implements WorkflowStrategy {
    @Override
    public void executeWorkflow(List<WorkflowStep> steps) {
        for (WorkflowStep step : steps) {
            CustomLogger.getInstance().info("Executing workflow step: " + step.getStepId(), "WorkflowStrategy");
            TaskService.getInstance().updateTaskStatus(step.getTask().getId(), TaskStatus.IN_PROGRESS);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            TaskService.getInstance().updateTaskStatus(step.getTask().getId(), TaskStatus.COMPLETED);
            step.setExecuted(true);
        }
    }
}
