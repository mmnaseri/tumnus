package com.mmnaseri.projects.tumnus.service.dto;

import com.mmnaseri.projects.tumnus.domain.entity.PersistentEntity;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:12 AM)
 */
@SuppressWarnings("unchecked")
public abstract class PersistentEntityDto<E extends PersistentEntity, D extends PersistentEntityDto<E, D>> extends BaseDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public D setId(Long id) {
        this.id = id;
        return (D) this;
    }

    public final E toEntity() {
        return (E) convertToEntity().setId(id);
    }

    public final D fromEntity(E entity) {
        setId(entity.getId());
        convertFromEntity(entity);
        return (D) this;
    }

    protected abstract E convertToEntity();

    protected abstract void convertFromEntity(E entity);

}
