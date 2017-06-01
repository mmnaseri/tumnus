package com.mmnaseri.projects.tumnus.web.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (5/16/17, 6:24 AM)
 */
@Configuration
@EnableJpaRepositories("com.mmnaseri.projects.tumnus.domain.repository")
@EnableJpaAuditing
@EntityScan("com.mmnaseri.projects.tumnus.domain.entity")
@EnableTransactionManagement
public class DataAccessConfiguration {
}
