package com.mmnaseri.projects.tumnus.service.contract;

import java.util.concurrent.Future;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 9:50 AM)
 */
public interface IdentifiableFuture<V> extends Future<V> {

    Long getId();

}
