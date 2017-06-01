package com.mmnaseri.projects.tumnus.service.contract;

import com.mmnaseri.projects.tumnus.service.dto.SessionDto;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:09 AM)
 */
public interface SessionService {

    SessionDto authenticate(String email, String password);

}
