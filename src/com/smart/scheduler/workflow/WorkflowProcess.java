package com.smart.scheduler.workflow;

import java.util.ArrayList;
import java.util.List;

public class WorkflowProcess {
    private final String id;
    private final String name;
    private final List<WorkflowStep> steps;

    public WorkflowProcess(String id, String name) {
        this.id = id;
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<WorkflowStep> getSteps() {
        return steps;
    }

    public void addStep(WorkflowStep step) {
        steps.add(step);
    }
}
