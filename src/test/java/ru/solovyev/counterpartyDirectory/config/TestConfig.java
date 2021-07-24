package ru.solovyev.counterpartyDirectory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;
import ru.solovyev.counterpartyDirectory.validator.CounterpartyValidator;

import static org.mockito.Mockito.mock;

/**
 * Конфигурация приложения для тестирования валидатора
 */
@Configuration
public class TestConfig {

    @Bean
    public CounterpartyValidator counterpartyValidator() {
        return new CounterpartyValidator(counterpartyService());
    }

    @Bean
    public CounterpartyService counterpartyService() {
        return mock(CounterpartyService.class);
    }
}
