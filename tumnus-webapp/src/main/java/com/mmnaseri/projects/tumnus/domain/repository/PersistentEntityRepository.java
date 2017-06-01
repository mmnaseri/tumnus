package com.mmnaseri.projects.tumnus.domain.repository;

import com.mmnaseri.projects.tumnus.domain.entity.PersistentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 5:59 AM)
 */
@NoRepositoryBean
public interface PersistentEntityRepository<E extends PersistentEntity> extends CrudRepository<E, Long> {
}
