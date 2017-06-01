package com.mmnaseri.projects.tumnus.domain.repository;

import com.mmnaseri.projects.tumnus.domain.entity.Permission;
import com.mmnaseri.projects.tumnus.domain.entity.User;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:02 AM)
 */
public interface PermissionRepository extends PersistentEntityRepository<Permission> {

    Set<Permission> findByUserAndSubjectStartingWith(User user, String subject);

    Set<Permission> findByUser(User user);
}
