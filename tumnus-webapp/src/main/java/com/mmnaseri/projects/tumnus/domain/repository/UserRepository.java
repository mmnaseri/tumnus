package com.mmnaseri.projects.tumnus.domain.repository;

import com.mmnaseri.projects.tumnus.domain.entity.User;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 5:59 AM)
 */
public interface UserRepository extends PersistentEntityRepository<User> {

    boolean existsByEmailIgnoreCase(String email);

    User findByEmailIgnoreCase(String email);
}
