package com.mmnaseri.projects.tumnus.service.contract;

import com.mmnaseri.projects.tumnus.service.dto.PermissionDto;
import com.mmnaseri.projects.tumnus.service.dto.UserDto;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:05 AM)
 */
public interface PermissionService {

    PermissionDto grant(UserDto user, String subject, String action);

    void revoke(UserDto user, String subject, String action);

    void lock(PermissionDto permission);

    void unlock(PermissionDto permission);

    Set<PermissionDto> recall(UserDto userDto);
}
