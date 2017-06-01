package com.mmnaseri.projects.tumnus.web.dto;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/20/17, 1:57 PM)
 */
public class LoginRequestDto {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public LoginRequestDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequestDto setPassword(String password) {
        this.password = password;
        return this;
    }

}
