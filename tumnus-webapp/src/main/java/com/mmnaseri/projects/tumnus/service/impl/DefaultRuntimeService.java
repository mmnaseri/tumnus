package com.mmnaseri.projects.tumnus.service.impl;

import com.mmnaseri.projects.tumnus.domain.entity.Task;
import com.mmnaseri.projects.tumnus.service.contract.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:33 AM)
 */
public class DefaultRuntimeService implements RuntimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeService.class);
    private final ExecutorService executor;
    private final TaskService taskService;
    private final Set<StartupTask> tasks;
    private final Queue<ScheduledTask> queue;
    private RuntimePhase currentPhase;

    public DefaultRuntimeService(TaskService taskService, Set<StartupTask> tasks) {
        this.taskService = taskService;
        this.tasks = tasks;
        final int corePoolSize = Runtime.getRuntime().availableProcessors() * 4;
        final BlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<>();
        executor = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2, 1L, TimeUnit.SECONDS, workQueue);
        changePhase(RuntimePhase.PRE_STARTUP);
        queue = new LinkedBlockingQueue<>();
    }

    private void changePhase(RuntimePhase currentPhase) {
        LOGGER.info("Initiating application phase: " + currentPhase);
        this.currentPhase = currentPhase;
    }

    @PostConstruct
    private void start() throws ExecutionException, InterruptedException {
        executor.submit(() -> {
            taskService.cleanUp();
            final Set<Future<Nothing>> futures = tasks.stream()
                    .map(this::submit)
                    .collect(Collectors.toSet());
            LOGGER.info("Found " + futures.size() + " startup task(s). Waiting for startup to finish.");
            changePhase(RuntimePhase.STARTUP);
            for (Future<Nothing> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    LOGGER.error("Failed to wait on startup task", e);
                }
            }
            changePhase(RuntimePhase.RUNNING);
        });
    }

    @PreDestroy
    @Override
    public void stop() throws InterruptedException {
        changePhase(RuntimePhase.SHUTDOWN);
        executor.awaitTermination(0, TimeUnit.SECONDS);
        changePhase(RuntimePhase.TERMINATED);
    }

    @Override
    public <V> Future<V> submit(RuntimeTask<V> task) {
        if (task.getPhase().compareTo(currentPhase) < 0) {
            throw new IllegalStateException("Scheduling task for " + task.getPhase() + " while in " + currentPhase);
        }
        LOGGER.info("Submitting task " + task.getId());
        taskService.markSubmitted(task);
        final ScheduledFuture<V> future = new ScheduledFuture<>(task.getId());
        queue.add(new ScheduledTask(task, future));
        return future;
    }

    @Override
    public <V> Future<V> submit(Callable<V> callable) {
        return submit(taskService.createTask(callable));
    }

    @Override
    public void run() {
        while (currentPhase.compareTo(RuntimePhase.TERMINATED) < 0) {
            try {
                ScheduledTask task = nextTask();
                if (task != null) {
                    final Future<?> future = executor.submit(() -> {
                        try {
                            LOGGER.info("Starting execution for task " + task.getTask().getId());
                            taskService.markInProgress(task.getTask());
                            final Object result = task.getTask().call();
                            LOGGER.info("Successfully finished task " + task.getTask().getId());
                            taskService.markDone(task.getTask());
                            return result;
                        } catch (Exception e) {
                            LOGGER.error("Task failed: " + task.getTask().getId(), e);
                            taskService.markFailed(task.getTask());
                            throw e;
                        }
                    });
                    final ScheduledFuture<?> taskFuture = task.getFuture();
                    taskFuture.setDelegate(future);
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private ScheduledTask nextTask() {
        final List<ScheduledTask> tasks = queue.stream().filter(scheduledTask -> scheduledTask.getTask().getPhase().equals(currentPhase)).collect(Collectors.toList());
        ScheduledTask task = null;
        for (ScheduledTask scheduledTask : tasks) {
            final RuntimeTask<?> runtimeTask = scheduledTask.getTask();
            LOGGER.info("Appraising " + runtimeTask.getId());
            if (!Task.Status.SUBMITTED.equals(runtimeTask.getStatus()) && !Task.Status.BLOCKED.equals(runtimeTask.getStatus())) {
                LOGGER.info("Removing task " + runtimeTask.getId() + " from the queue since its status is " + runtimeTask.getStatus());
                queue.remove(scheduledTask);
                continue;
            }
            final Set<Task.Status> taskStatuses = runtimeTask.getDependencies().stream().map(RuntimeTask::getStatus).collect(Collectors.toSet());
            if (taskStatuses.stream().filter(Task.Status.FAILED::equals).count() > 0) {
                LOGGER.warn("Marking task " + runtimeTask.getId() + " as failed due to failed dependencies");
                taskService.markFailed(runtimeTask);
                continue;
            }
            if (taskStatuses.stream().filter(status -> !Task.Status.DONE.equals(status)).count() > 0) {
                LOGGER.info("Marking task " + runtimeTask.getId() + " as blocked");
                taskService.markBlocked(runtimeTask);
                continue;
            }
            if (task == null) {
                task = scheduledTask;
                LOGGER.info("Picking task " + runtimeTask.getId() + " for execution");
                queue.remove(task);
            }
        }
        return task;
    }

    private static class ScheduledTask {

        private final RuntimeTask<?> task;
        private final ScheduledFuture<?> future;

        private ScheduledTask(RuntimeTask<?> task, ScheduledFuture<?> future) {
            this.task = task;
            this.future = future;
        }

        private RuntimeTask<?> getTask() {
            return task;
        }

        private ScheduledFuture<?> getFuture() {
            return future;
        }

    }

}
