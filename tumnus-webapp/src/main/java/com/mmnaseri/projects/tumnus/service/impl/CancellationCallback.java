package com.mmnaseri.projects.tumnus.service.impl;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 7:35 AM)
 */
@FunctionalInterface
public interface CancellationCallback {

    boolean cancel(boolean mayInterruptIfRunning) throws Exception;

}
