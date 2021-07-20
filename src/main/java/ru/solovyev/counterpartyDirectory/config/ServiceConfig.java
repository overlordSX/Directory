package ru.solovyev.counterpartyDirectory.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "ru.solovyev.counterpartyDirectory.repository")
@EntityScan("ru.solovyev.counterpartyDirectory.entity")
public class ServiceConfig {
}
