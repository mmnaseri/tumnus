package com.mmnaseri.projects.tumnus.web.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 1:27 PM)
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    public WebSecurityConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/lib/**", "/login/**", "/rest/v1/sessions").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.html").permitAll().and()
                .logout().permitAll();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return authenticationManager;
    }

}
