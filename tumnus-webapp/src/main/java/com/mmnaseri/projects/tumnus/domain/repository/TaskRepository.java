package com.mmnaseri.projects.tumnus.domain.repository;

import com.mmnaseri.projects.tumnus.domain.entity.Task;

import javax.transaction.Transactional;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:55 AM)
 */
public interface TaskRepository extends PersistentEntityRepository<Task> {

    @Transactional
    void deleteByStatusNot(Task.Status status);

}
