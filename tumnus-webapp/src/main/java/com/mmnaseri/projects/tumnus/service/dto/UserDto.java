package com.mmnaseri.projects.tumnus.service.dto;

import com.mmnaseri.projects.tumnus.domain.entity.User;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:10 AM)
 */
public class UserDto extends PersistentEntityDto<User, UserDto> {

    private String email;
    private String name;

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected User convertToEntity() {
        return new User()
                .setName(getName())
                .setEmail(getEmail());
    }

    @Override
    protected void convertFromEntity(User entity) {
        setEmail(entity.getEmail());
        setName(entity.getName());
    }

    @Override
    public String toString() {
        return email;
    }

}
