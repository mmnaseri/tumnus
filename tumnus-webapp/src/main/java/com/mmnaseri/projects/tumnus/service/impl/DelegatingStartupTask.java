package com.mmnaseri.projects.tumnus.service.impl;

import com.mmnaseri.projects.tumnus.domain.entity.Task;
import com.mmnaseri.projects.tumnus.service.contract.Nothing;
import com.mmnaseri.projects.tumnus.service.contract.RuntimeTask;
import com.mmnaseri.projects.tumnus.service.contract.StartupTask;

import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 7:16 AM)
 */
public class DelegatingStartupTask extends StartupTask {

    private final RuntimeTask<Nothing> delegate;

    public DelegatingStartupTask(RuntimeTask<Nothing> delegate) {
        super(delegate.getId(), delegate.getDependencies().stream().map(dependency -> (StartupTask) dependency).collect(Collectors.toSet()));
        this.delegate = delegate;
    }

    @Override
    public Nothing call() throws Exception {
        delegate.call();
        return Nothing.VALUE;
    }

    @Override
    public Task.Status getStatus() {
        return delegate.getStatus();
    }

}
