package com.smart.scheduler.workflow;

import com.smart.scheduler.logger.CustomLogger;

public class WorkflowEngine {
    private static WorkflowEngine instance;
    private WorkflowStrategy strategy;

    private WorkflowEngine() {
        this.strategy = new SequentialWorkflowStrategy();
    }

    public static synchronized WorkflowEngine getInstance() {
        if (instance == null) {
            instance = new WorkflowEngine();
        }
        return instance;
    }

    public void setStrategy(WorkflowStrategy strategy) {
        this.strategy = strategy;
    }

    public void runWorkflow(WorkflowProcess process) {
        CustomLogger.getInstance().info("Starting workflow process: " + process.getName(), "WorkflowEngine");
        strategy.executeWorkflow(process.getSteps());
        CustomLogger.getInstance().info("Finished workflow process: " + process.getName(), "WorkflowEngine");
    }
}
