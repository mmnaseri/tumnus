package com.mmnaseri.projects.tumnus.web.controller;

import com.mmnaseri.projects.tumnus.service.dto.SessionDto;
import com.mmnaseri.projects.tumnus.web.dto.LoginRequestDto;
import com.mmnaseri.projects.tumnus.web.security.SessionAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 1:56 PM)
 */
@RestController
@RequestMapping("/rest/v1/sessions")
public class SessionsController {

    private final AuthenticationManager authenticationManager;

    public SessionsController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(method = RequestMethod.POST, path = {"", "/"})
    public SessionDto create(@RequestBody LoginRequestDto request) {
        SecurityContextHolder.clearContext();
        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(context);
        final SessionAuthenticationToken authentication = ((SessionAuthenticationToken) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())));
        context.setAuthentication(authentication);
        return authentication.getSession();
    }

}
