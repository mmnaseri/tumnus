package com.mmnaseri.projects.tumnus.service.impl;

import com.mmnaseri.projects.tumnus.domain.entity.Task;
import com.mmnaseri.projects.tumnus.domain.repository.TaskRepository;
import com.mmnaseri.projects.tumnus.service.contract.*;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 7:06 AM)
 */
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;

    public DefaultTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private Task createTask(RuntimeTask[] dependencies) {
        final Task task = new Task();
        task.setStatus(Task.Status.NEW);
        task.setDependencies(
                Arrays.stream(dependencies)
                        .map(RuntimeTask::getId)
                        .map(id -> (Task) new Task().setId(id))
                        .collect(Collectors.toSet())
        );
        return taskRepository.save(task);
    }

    private <T extends RuntimeTask<?>> Set<T> toSet(T[] dependencies) {
        return new CopyOnWriteArraySet<>(Arrays.asList(dependencies));
    }

    private void changeStatus(RuntimeTask<?> task, Task.Status status) {
        final Task found = taskRepository.findOne(task.getId());
        if (found == null) {
            throw new IllegalStateException("Task not found: " + task.getId());
        }
        found.setStatus(status);
        taskRepository.save(found);
    }

    @Override
    public StartupTask createStartupTask(Runnable delegate, StartupTask... dependencies) {
        return new DelegatingStartupTask(createTask(() -> {
            delegate.run();
            return Nothing.VALUE;
        }, RuntimePhase.STARTUP, dependencies));
    }

    @Override
    public <V> RuntimeTask<V> createTask(Callable<V> delegate, RuntimePhase phase, RuntimeTask<?>[] dependencies) {
        final Task task = createTask(dependencies);
        return new RuntimeTask<V>(task.getId(), phase, toSet(dependencies)) {
            @Override
            public Task.Status getStatus() {
                final Task found = taskRepository.findOne(getId());
                if (found == null) {
                    throw new IllegalStateException("Task does not exist");
                }
                return found.getStatus();
            }

            @Override
            public V call() throws Exception {
                return delegate.call();
            }
        };
    }

    @Override
    public void markSubmitted(RuntimeTask<?> task) {
        changeStatus(task, Task.Status.SUBMITTED);
    }

    @Override
    public void markInProgress(RuntimeTask<?> task) {
        changeStatus(task, Task.Status.IN_PROGRESS);
    }

    @Override
    public void markFailed(RuntimeTask<?> task) {
        changeStatus(task, Task.Status.FAILED);
    }

    @Override
    public void markDone(RuntimeTask<?> task) {
        changeStatus(task, Task.Status.DONE);
    }

    @Override
    public void markBlocked(RuntimeTask<?> task) {
        changeStatus(task, Task.Status.BLOCKED);
    }

    @Override
    public void cleanUp() {
        taskRepository.deleteByStatusNot(Task.Status.NEW);
    }

}
