package com.mmnaseri.projects.tumnus.web.controller;

import com.mmnaseri.projects.tumnus.service.contract.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 1:59 PM)
 */
@Configuration
public class ControllerConfiguration {

    @Bean
    public SessionsController sessionsController(AuthenticationManager authenticationManager) {
        return new SessionsController(authenticationManager);
    }

    @Bean
    public UserController userController(UserService userService) {
        return new UserController(userService);
    }

}
