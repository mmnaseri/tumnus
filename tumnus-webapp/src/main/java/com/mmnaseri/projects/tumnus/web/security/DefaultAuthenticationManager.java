package com.mmnaseri.projects.tumnus.web.security;

import com.mmnaseri.projects.tumnus.service.contract.SessionService;
import com.mmnaseri.projects.tumnus.service.dto.SessionDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 2:09 PM)
 */
public class DefaultAuthenticationManager implements AuthenticationManager {

    private final SessionService sessionService;

    public DefaultAuthenticationManager(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = (String) authentication.getPrincipal();
        final String password = (String) authentication.getCredentials();
        final SessionDto sessionDto;
        try {
            sessionDto = sessionService.authenticate(email, password);
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to authenticate the user via the provided username/password combination", e);
        }
        return new SessionAuthenticationToken(email, password, sessionDto);
    }

}
