package com.mmnaseri.projects.tumnus.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 5:56 AM)
 */
@Entity
@Table(name = "users")
public class User extends PersistentEntity {

    private String password;
    private String email;
    private String name;

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
