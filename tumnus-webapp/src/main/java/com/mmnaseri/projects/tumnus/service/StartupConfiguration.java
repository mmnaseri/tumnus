package com.mmnaseri.projects.tumnus.service;

import com.mmnaseri.projects.tumnus.domain.repository.PermissionRepository;
import com.mmnaseri.projects.tumnus.domain.repository.UserRepository;
import com.mmnaseri.projects.tumnus.service.contract.PermissionService;
import com.mmnaseri.projects.tumnus.service.contract.StartupTask;
import com.mmnaseri.projects.tumnus.service.contract.TaskService;
import com.mmnaseri.projects.tumnus.service.contract.UserService;
import com.mmnaseri.projects.tumnus.service.dto.PermissionDto;
import com.mmnaseri.projects.tumnus.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 9:06 AM)
 */
@Configuration
public class StartupConfiguration {

    @Bean
    public StartupTask wipeDatabaseStartupTask(TaskService taskService,
                                               UserRepository userRepository,
                                               PermissionRepository permissionRepository) {
        return taskService.createStartupTask(() -> {
            permissionRepository.deleteAll();
            userRepository.deleteAll();
        });
    }

    @Bean
    public StartupTask createBasicUsersStartupTask(
            @Qualifier("wipeDatabaseStartupTask") StartupTask wipeDatabaseStartupTask,
            UserService userService,
            TaskService taskService
    ) {
        return taskService.createStartupTask(() -> {
            userService.create("root@localhost", "root-123456");
        }, wipeDatabaseStartupTask);
    }

    @Bean
    public StartupTask createBasicPermissionsStartupTask(
            @Qualifier("wipeDatabaseStartupTask") StartupTask wipeDatabaseStartupTask,
            @Qualifier("createBasicUsersStartupTask") StartupTask createBasicUsersStartupTask,
            PermissionService permissionService,
            TaskService taskService
    ) {
        return taskService.createStartupTask(() -> {
            final UserDto user = new UserDto().setEmail("root@localhost");
            final PermissionDto permission = permissionService.grant(user, "*", "*");
            permissionService.lock(permission);
        }, wipeDatabaseStartupTask, createBasicUsersStartupTask);
    }


}
