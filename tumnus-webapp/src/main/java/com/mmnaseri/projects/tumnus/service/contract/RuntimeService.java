package com.mmnaseri.projects.tumnus.service.contract;

import javax.annotation.PreDestroy;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:08 AM)
 */
public interface RuntimeService extends Runnable {

    @PreDestroy
    void stop() throws InterruptedException;

    <V> Future<V> submit(RuntimeTask<V> task);

    <V> Future<V> submit(Callable<V> callable);

    default Future<?> submit(Runnable runnable) {
        return submit(() -> {
            runnable.run();
            return Nothing.VALUE;
        });
    }

}
