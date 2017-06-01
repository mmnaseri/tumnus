package com.mmnaseri.projects.tumnus.web.app;

import com.mmnaseri.projects.tumnus.service.ServiceConfiguration;
import com.mmnaseri.projects.tumnus.service.StartupConfiguration;
import com.mmnaseri.projects.tumnus.web.config.DataAccessConfiguration;
import com.mmnaseri.projects.tumnus.web.controller.ControllerConfiguration;
import com.mmnaseri.projects.tumnus.web.security.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 5:16 AM)
 */
@SpringBootApplication
@ComponentScan(useDefaultFilters = false)
@Import({
        DataAccessConfiguration.class,
        ServiceConfiguration.class,
        StartupConfiguration.class,
        SecurityConfiguration.class,
        ControllerConfiguration.class
})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
