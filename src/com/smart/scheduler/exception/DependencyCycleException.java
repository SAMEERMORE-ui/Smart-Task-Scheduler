package com.smart.scheduler.exception;

public class DependencyCycleException extends AppException {
    public DependencyCycleException(String message) {
        super(message);
    }
}
