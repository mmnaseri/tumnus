package com.mmnaseri.projects.tumnus.service.dto;

import com.mmnaseri.projects.tumnus.domain.entity.Permission;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:15 AM)
 */
public class PermissionDto extends PersistentEntityDto<Permission, PermissionDto> {

    private UserDto user;
    private String action;
    private String subject;
    private Boolean locked;

    public UserDto getUser() {
        return user;
    }

    private PermissionDto setUser(UserDto user) {
        this.user = user;
        return this;
    }

    public String getAction() {
        return action;
    }

    private PermissionDto setAction(String action) {
        this.action = action;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    private PermissionDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Boolean getLocked() {
        return locked;
    }

    private PermissionDto setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    @Override
    protected Permission convertToEntity() {
        return new Permission()
                .setLocked(getLocked())
                .setAction(getAction())
                .setSubject(getSubject())
                .setUser(getUser() == null ? null : getUser().toEntity());
    }

    @Override
    protected void convertFromEntity(Permission entity) {
        setLocked(entity.getLocked());
        setAction(entity.getAction());
        setSubject(entity.getSubject());
        if (entity.getUser() != null) {
            setUser(new UserDto().fromEntity(entity.getUser()));
        }
    }

}
