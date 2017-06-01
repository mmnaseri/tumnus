package com.mmnaseri.projects.tumnus.domain.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 5:58 AM)
 */
@MappedSuperclass
public abstract class PersistentEntity {

    @Id
    @Column
    @GeneratedValue
    private Long id;
    @CreatedBy
    @Column
    private String createdBy;
    @CreatedDate
    @Column
    private Date createdAt;
    @LastModifiedBy
    @Column
    private String modifiedBy;
    @LastModifiedDate
    @Column
    private Date modifiedAt;

    public Long getId() {
        return id;
    }

    public PersistentEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PersistentEntity setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public PersistentEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public PersistentEntity setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public PersistentEntity setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

}
