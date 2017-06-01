package com.mmnaseri.projects.tumnus.service.contract;

import com.mmnaseri.projects.tumnus.service.dto.UserDto;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:00 AM)
 */
public interface UserService {

    UserDto create(String email, String password);

    void delete(UserDto user);

    void update(UserDto user);

    void updatePassword(UserDto user, String old, String substitute);

    UserDto lookUp(String email);

}
