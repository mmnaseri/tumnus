package com.mmnaseri.projects.tumnus.service;

import com.mmnaseri.projects.tumnus.domain.repository.PermissionRepository;
import com.mmnaseri.projects.tumnus.domain.repository.TaskRepository;
import com.mmnaseri.projects.tumnus.domain.repository.UserRepository;
import com.mmnaseri.projects.tumnus.service.contract.*;
import com.mmnaseri.projects.tumnus.service.impl.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:33 AM)
 */
@Configuration
public class ServiceConfiguration {

    @Bean
    public TaskService taskService(TaskRepository taskRepository) {
        return new DefaultTaskService(taskRepository);
    }

    @Bean
    public RuntimeService runtimeService(@Qualifier("taskService") TaskService taskService, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<Set<StartupTask>> tasks) {
        return new DefaultRuntimeService(taskService, tasks.orElse(Collections.emptySet()));
    }

    @Bean
    public Thread runtimeServiceThread(@Qualifier("runtimeService") RuntimeService runtimeService) {
        final Thread thread = new Thread(runtimeService);
        thread.start();
        return thread;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService(UserRepository userRepository, PasswordEncoder encoder) {
        return new DefaultUserService(userRepository, encoder);
    }

    @Bean
    public PermissionService permissionService(UserRepository userRepository, PermissionRepository permissionRepository) {
        return new DefaultPermissionService(userRepository, permissionRepository);
    }

    @Bean
    public SessionService sessionService(PasswordEncoder passwordEncoder, UserRepository userRepository, PermissionService permissionService) {
        return new DefaultSessionService(passwordEncoder, userRepository, permissionService);
    }

}
