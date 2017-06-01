package com.mmnaseri.projects.tumnus.service.contract;

import java.util.Collections;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:49 AM)
 */
public abstract class StartupTask extends RuntimeTask<Nothing> {

    protected StartupTask(Long id) {
        this(id, Collections.emptySet());
    }

    protected StartupTask(Long id, Set<StartupTask> dependencies) {
        super(id, RuntimePhase.STARTUP, dependencies);
    }

}
