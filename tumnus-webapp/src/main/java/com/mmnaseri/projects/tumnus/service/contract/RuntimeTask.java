package com.mmnaseri.projects.tumnus.service.contract;

import com.mmnaseri.projects.tumnus.domain.entity.Task;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:59 AM)
 */
public abstract class RuntimeTask<V> implements Callable<V> {

    private final Long id;
    private final Set<? extends RuntimeTask<?>> dependencies;
    private final RuntimePhase phase;

    public RuntimeTask(Long id, RuntimePhase phase) {
        this(id, phase, Collections.emptySet());
    }

    public RuntimeTask(Long id, RuntimePhase phase, Set<? extends RuntimeTask<?>> dependencies) {
        this.id = id;
        this.phase = phase;
        this.dependencies = new CopyOnWriteArraySet<>(dependencies);
    }

    public final Long getId() {
        return id;
    }

    public final Set<? extends RuntimeTask<?>> getDependencies() {
        return dependencies;
    }

    public final RuntimePhase getPhase() {
        return phase;
    }

    public abstract Task.Status getStatus();

}
