package com.mmnaseri.projects.tumnus.domain.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:53 AM)
 */
@Entity
@Table(name = "tasks")
public class Task extends PersistentEntity {

    @ManyToMany(targetEntity = Task.class)
    private Set<Task> dependencies;
    private Status status;

    public Set<Task> getDependencies() {
        return dependencies;
    }

    public Task setDependencies(Set<Task> dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    public Task setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {

        NEW, SUBMITTED, IN_PROGRESS, FAILED, DONE, BLOCKED

    }

}
