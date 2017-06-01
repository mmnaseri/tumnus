package com.mmnaseri.projects.tumnus.service.impl;

import com.mmnaseri.projects.tumnus.service.contract.IdentifiableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 8:03 AM)
 */
public class ScheduledFuture<V> implements IdentifiableFuture<V> {

    private final Long id;
    private Future<V> delegate = null;
    private boolean cancelled = false;

    public ScheduledFuture(Long id) {
        this.id = id;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (delegate == null) {
            cancelled = true;
            return true;
        }
        return delegate.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return cancelled || (delegate != null && delegate.isCancelled());
    }

    @Override
    public boolean isDone() {
        return delegate != null && delegate.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        while (true) {
            if (isDone()) {
                return delegate.get();
            }
            Thread.sleep(100);
        }
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        final long duration = unit.toMillis(timeout);
        final long start = System.currentTimeMillis();
        while (true) {
            if (isDone()) {
                return delegate.get();
            }
            if (System.currentTimeMillis() - duration > start) {
                throw new TimeoutException();
            }
            Thread.sleep(100);
        }
    }

    public Future<V> getDelegate() {
        return delegate;
    }

    public void setDelegate(Future<?> delegate) {
        //noinspection unchecked
        this.delegate = (Future<V>) delegate;
        if (cancelled) {
            delegate.cancel(true);
        }
    }

    @Override
    public Long getId() {
        return id;
    }
}
