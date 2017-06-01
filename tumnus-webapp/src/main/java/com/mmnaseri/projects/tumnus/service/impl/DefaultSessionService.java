package com.mmnaseri.projects.tumnus.service.impl;

import com.mmnaseri.projects.tumnus.domain.entity.User;
import com.mmnaseri.projects.tumnus.domain.repository.UserRepository;
import com.mmnaseri.projects.tumnus.service.contract.PermissionService;
import com.mmnaseri.projects.tumnus.service.contract.SessionService;
import com.mmnaseri.projects.tumnus.service.dto.SessionDto;
import com.mmnaseri.projects.tumnus.service.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 2:00 PM)
 */
public class DefaultSessionService implements SessionService {

    private static final String MESSAGE = "Invalid user/password combination";
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    public DefaultSessionService(PasswordEncoder passwordEncoder, UserRepository userRepository, PermissionService permissionService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    @Override
    public SessionDto authenticate(String email, String password) {
        final User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new IllegalStateException(MESSAGE);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException(MESSAGE);
        }
        final SessionDto sessionDto = new SessionDto();
        final UserDto userDto = new UserDto().fromEntity(user);
        sessionDto.setUser(userDto);
        sessionDto.setPermissions(permissionService.recall(userDto));
        return sessionDto;
    }

}
