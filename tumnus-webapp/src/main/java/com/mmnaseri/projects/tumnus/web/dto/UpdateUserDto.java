package com.mmnaseri.projects.tumnus.web.dto;

import com.mmnaseri.projects.tumnus.service.dto.BaseDto;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/21/17, 9:47 PM)
 */
public class UpdateUserDto extends BaseDto {

    private Long id;
    private String email;
    private String name;
    private String currentPassword;
    private String newPassword;

    public Long getId() {
        return id;
    }

    public UpdateUserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UpdateUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UpdateUserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UpdateUserDto setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public UpdateUserDto setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        return this;
    }
}
