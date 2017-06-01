package com.mmnaseri.projects.tumnus.service.impl;

import com.mmnaseri.projects.tumnus.domain.entity.User;
import com.mmnaseri.projects.tumnus.domain.repository.UserRepository;
import com.mmnaseri.projects.tumnus.service.contract.UserService;
import com.mmnaseri.projects.tumnus.service.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 9:49 AM)
 */
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public DefaultUserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDto create(String email, String password) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalStateException("Email is already in use");
        }
        final User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        return new UserDto().fromEntity(user);
    }

    @Override
    public void delete(UserDto user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getId());
        if (!userRepository.exists(user.getId())) {
            throw new IllegalStateException();
        }
        userRepository.delete(user.getId());
    }

    @Override
    public void update(UserDto user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getId());
        if (!userRepository.exists(user.getId())) {
            throw new IllegalStateException();
        }
        final User found = userRepository.findOne(user.getId());
        if (user.getEmail() != null && !found.getEmail().equals(user.getEmail()) && userRepository.existsByEmailIgnoreCase(found.getEmail())) {
            throw new IllegalStateException("Another user with this email address already exists");
        }
        found.setEmail(user.getEmail() == null ? found.getEmail() : user.getEmail());
        found.setName(user.getName() == null ? found.getName() : user.getName());
        userRepository.save(found);
    }

    @Override
    public void updatePassword(UserDto user, String old, String substitute) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getId());
        Objects.requireNonNull(old);
        Objects.requireNonNull(substitute);
        if (!userRepository.exists(user.getId())) {
            throw new IllegalStateException();
        }
        final User found = userRepository.findOne(user.getId());
        if (!encoder.matches(old, found.getPassword())) {
            throw new IllegalArgumentException("Invalid password for " + user);
        }
        found.setPassword(encoder.encode(substitute));
        userRepository.save(found);
    }

    @Override
    public UserDto lookUp(String email) {
        return new UserDto().fromEntity(userRepository.findByEmailIgnoreCase(email));
    }

}
