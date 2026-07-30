package com.smart.scheduler.scheduler;

import com.smart.scheduler.logger.CustomLogger;
import com.smart.scheduler.task.Task;
import com.smart.scheduler.task.TaskService;

import java.util.concurrent.*;

public class TaskSchedulerService {
    private static TaskSchedulerService instance;
    private final ScheduledExecutorService scheduler;
    private final ExecutorService workerPool;
    private final BlockingQueue<Task> taskQueue;

    private TaskSchedulerService() {
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.workerPool = Executors.newFixedThreadPool(4);
        this.taskQueue = new LinkedBlockingQueue<>();
    }

    public static synchronized TaskSchedulerService getInstance() {
        if (instance == null) {
            instance = new TaskSchedulerService();
        }
        return instance;
    }

    public void scheduleTask(String taskId, long delaySeconds) {
        scheduler.schedule(() -> {
            Task task = TaskService.getInstance().getTask(taskId);
            workerPool.submit(new TaskExecutionWorker(task));
        }, delaySeconds, TimeUnit.SECONDS);
        CustomLogger.getInstance().info("Scheduled task " + taskId + " in " + delaySeconds + "s", "TaskSchedulerService");
    }

    public void scheduleRecurring(String taskId, long initialDelaySeconds, long periodSeconds) {
        scheduler.scheduleAtFixedRate(() -> {
            Task task = TaskService.getInstance().getTask(taskId);
            workerPool.submit(new TaskExecutionWorker(task));
        }, initialDelaySeconds, periodSeconds, TimeUnit.SECONDS);
        CustomLogger.getInstance().info("Scheduled recurring task " + taskId + " every " + periodSeconds + "s", "TaskSchedulerService");
    }

    public void enqueueTaskForImmediateWorker(Task task) {
        taskQueue.offer(task);
        workerPool.submit(() -> {
            Task nextTask = taskQueue.poll();
            if (nextTask != null) {
                new TaskExecutionWorker(nextTask).run();
            }
        });
    }

    public void shutdown() {
        scheduler.shutdown();
        workerPool.shutdown();
    }
}
