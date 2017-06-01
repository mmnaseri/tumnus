package com.mmnaseri.projects.tumnus.web.security;

import com.mmnaseri.projects.tumnus.service.dto.SessionDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 2:18 PM)
 */
public class SessionAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final SessionDto session;

    public SessionAuthenticationToken(String email, String password, SessionDto sessionDto) {
        super(email, password, sessionDto.getPermissions().stream().map(ActionBasedGrantedAuthority::new).collect(Collectors.toSet()));
        this.session = sessionDto;
    }

    public SessionDto getSession() {
        return session;
    }

}
