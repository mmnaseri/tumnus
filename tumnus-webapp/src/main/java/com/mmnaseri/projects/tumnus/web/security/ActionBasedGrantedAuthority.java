package com.mmnaseri.projects.tumnus.web.security;

import com.mmnaseri.projects.tumnus.domain.entity.Permission;
import com.mmnaseri.projects.tumnus.service.dto.PermissionDto;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 2:12 PM)
 */
public class ActionBasedGrantedAuthority implements GrantedAuthority {

    private final Permission permission;

    public ActionBasedGrantedAuthority(PermissionDto permissionDto) {
        this(permissionDto.toEntity());
    }

    public ActionBasedGrantedAuthority(Permission permission) {
        this.permission = permission;
    }

    public String getAction() {
        return permission.getAction();
    }

    public String getSubject() {
        return permission.getSubject();
    }

    @Override
    public String getAuthority() {
        return getAction() + "@" + getSubject();
    }
}
