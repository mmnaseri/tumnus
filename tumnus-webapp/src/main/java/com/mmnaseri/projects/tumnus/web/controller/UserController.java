package com.mmnaseri.projects.tumnus.web.controller;

import com.mmnaseri.projects.tumnus.service.contract.UserService;
import com.mmnaseri.projects.tumnus.service.dto.UserDto;
import com.mmnaseri.projects.tumnus.web.dto.UpdateUserDto;
import com.mmnaseri.projects.tumnus.web.security.SessionAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/21/17, 9:45 PM)
 */
@RestController
@RequestMapping("/rest/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, path = {"/me", "/me/"})
    public UserDto me() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            throw new IllegalStateException();
        }
        final Authentication authentication = context.getAuthentication();
        if (authentication == null || !(authentication instanceof SessionAuthenticationToken)) {
            throw new IllegalArgumentException();
        }
        final SessionAuthenticationToken token = (SessionAuthenticationToken) authentication;
        return token.getSession().getUser();
    }

    @RequestMapping(method = RequestMethod.POST, path = {"/me", "/me/"})
    public UserDto updateMe(@RequestBody UpdateUserDto dto) {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            throw new IllegalStateException();
        }
        final Authentication authentication = context.getAuthentication();
        if (authentication == null || !(authentication instanceof SessionAuthenticationToken)) {
            throw new IllegalArgumentException();
        }
        final SessionAuthenticationToken token = (SessionAuthenticationToken) authentication;
        final UserDto sessionUser = token.getSession().getUser();
        final UserDto user = new UserDto().setId(sessionUser.getId()).setEmail(dto.getEmail()).setName(dto.getName());
        userService.update(user);
        if (dto.getNewPassword() != null) {
            userService.updatePassword(user, dto.getCurrentPassword(), dto.getNewPassword());
        }
        token.getSession().setUser(user);
        return user;
    }

}
