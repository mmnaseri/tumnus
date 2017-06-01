package com.mmnaseri.projects.tumnus.web.security;

import com.mmnaseri.projects.tumnus.service.contract.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 2:14 PM)
 */
@Configuration
public class SecurityConfiguration {

    @Bean
    public WebSecurityConfig webSecurityConfig(AuthenticationManager authenticationManager) {
        return new WebSecurityConfig(authenticationManager);
    }

    @Bean
    public AuthenticationManager authenticationManager(SessionService sessionService) {
        return new DefaultAuthenticationManager(sessionService);
    }

}
