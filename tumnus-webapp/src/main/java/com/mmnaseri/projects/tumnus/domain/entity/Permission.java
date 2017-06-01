package com.mmnaseri.projects.tumnus.domain.entity;

import javax.persistence.*;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 9:41 AM)
 */
@Entity
@Table(name = "permissions")
public class Permission extends PersistentEntity {

    @JoinColumn
    @ManyToOne
    private User user;
    @Column
    private String action;
    @Column
    private String subject;
    @Column
    private Boolean locked;

    public User getUser() {
        return user;
    }

    public Permission setUser(User user) {
        this.user = user;
        return this;
    }

    public String getAction() {
        return action;
    }

    public Permission setAction(String action) {
        this.action = action;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Permission setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Boolean getLocked() {
        return locked;
    }

    public Permission setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

}
