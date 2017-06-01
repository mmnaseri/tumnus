package com.mmnaseri.projects.tumnus.service.impl;

import com.mmnaseri.projects.tumnus.domain.entity.Permission;
import com.mmnaseri.projects.tumnus.domain.entity.User;
import com.mmnaseri.projects.tumnus.domain.repository.PermissionRepository;
import com.mmnaseri.projects.tumnus.domain.repository.UserRepository;
import com.mmnaseri.projects.tumnus.service.contract.PermissionService;
import com.mmnaseri.projects.tumnus.service.dto.PermissionDto;
import com.mmnaseri.projects.tumnus.service.dto.UserDto;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 8:26 PM)
 */
public class DefaultPermissionService implements PermissionService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public DefaultPermissionService(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    private User getUser(UserDto user) {
        Objects.requireNonNull(user);
        final User found;
        if (user.getId() != null) {
            found = userRepository.findOne(user.getId());
        } else if (user.getEmail() != null) {
            found = userRepository.findByEmailIgnoreCase(user.getEmail());
        } else {
            throw new IllegalStateException();
        }
        Objects.requireNonNull(found);
        return found;
    }

    private Permission getPermission(PermissionDto permission) {
        Objects.requireNonNull(permission);
        Objects.requireNonNull(permission.getId());
        final Permission found = permissionRepository.findOne(permission.getId());
        Objects.requireNonNull(found);
        return found;
    }

    @Override
    public PermissionDto grant(UserDto user, String subject, String action) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(subject);
        final User found = getUser(user);
        final Permission permission = new Permission();
        permission.setUser(found);
        permission.setAction(action);
        permission.setSubject(subject);
        permission.setLocked(false);
        return new PermissionDto().fromEntity(permissionRepository.save(permission));
    }

    @Override
    public void revoke(UserDto user, String subject, String action) {
        final User found = getUser(user);
        Set<Permission> permissions = permissionRepository.findByUserAndSubjectStartingWith(found, subject);
        permissions.stream()
                .filter(permission -> permission.getAction().startsWith(action) && Boolean.FALSE.equals(permission.getLocked()))
                .forEach(permissionRepository::delete);
    }

    @Override
    public void lock(PermissionDto permission) {
        final Permission found = getPermission(permission);
        found.setLocked(true);
        permissionRepository.save(found);
    }

    @Override
    public void unlock(PermissionDto permission) {
        final Permission found = getPermission(permission);
        found.setLocked(false);
        permissionRepository.save(found);
    }

    @Override
    public Set<PermissionDto> recall(UserDto userDto) {
        final Set<Permission> permissions = permissionRepository.findByUser(userDto.toEntity());
        return permissions.stream()
                .map(permission -> new PermissionDto().fromEntity(permission))
                .collect(Collectors.toSet());
    }

}
