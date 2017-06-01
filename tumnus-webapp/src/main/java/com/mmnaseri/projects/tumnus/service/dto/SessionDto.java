package com.mmnaseri.projects.tumnus.service.dto;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:21 AM)
 */
public class SessionDto extends BaseDto {

    private UserDto user;
    private Set<PermissionDto> permissions;

    public UserDto getUser() {
        return user;
    }

    public SessionDto setUser(UserDto user) {
        this.user = user;
        return this;
    }

    public Set<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDto> permissions) {
        this.permissions = permissions;
    }

}
