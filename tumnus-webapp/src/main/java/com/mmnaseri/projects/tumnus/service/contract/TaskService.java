package com.mmnaseri.projects.tumnus.service.contract;

import java.util.concurrent.Callable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:56 AM)
 */
public interface TaskService {

    StartupTask createStartupTask(Runnable delegate, StartupTask... dependencies);

    default <V> RuntimeTask<V> createTask(Callable<V> delegate, RuntimeTask<?>... dependencies) {
        return createTask(delegate, RuntimePhase.RUNNING, dependencies);
    }

    <V> RuntimeTask<V> createTask(Callable<V> delegate, RuntimePhase phase, RuntimeTask<?>... dependencies);

    void markSubmitted(RuntimeTask<?> task);

    void markInProgress(RuntimeTask<?> task);

    void markFailed(RuntimeTask<?> task);

    void markDone(RuntimeTask<?> task);

    void markBlocked(RuntimeTask<?> task);

    void cleanUp();
}
