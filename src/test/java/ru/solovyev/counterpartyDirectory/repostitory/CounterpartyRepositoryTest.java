package ru.solovyev.counterpartyDirectory.repostitory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.repository.CounterpartyRepository;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс тестирующий работу репозитория
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CounterpartyRepositoryTest {

    @Autowired
    CounterpartyRepository counterpartyRepository;

    private static Counterparty counterparty;
    private static Counterparty counterparty2;

    @BeforeAll
    public static void init() {
        counterparty = new Counterparty(
                1L,
                "Сбербанк",
                "7707083893",
                "773601001",
                "044525225",
                "30301810800006003800");
        counterparty2 = new Counterparty(
                2L,
                "ВТБ",
                "7702070139",
                "997950001",
                "044525000",
                "30101810700000000187");
    }


    /**
     * Тестируется поиск по Id
     */
    @Test
    public void findById() {
        Counterparty saved = counterpartyRepository.save(counterparty);
        Optional<Counterparty> founded = counterpartyRepository.findById(saved.getId());
        assertTrue(founded.isPresent());
        founded.ifPresent(value -> assertEquals(value.getId(), saved.getId()));
    }

    /**
     * Тестируется поиск всех контрагентов
     */
    @Test
    void findAll() {
        List<Counterparty> mustBeFounded = new ArrayList<>();
        mustBeFounded.add(counterpartyRepository.save(counterparty));
        mustBeFounded.add(counterpartyRepository.save(counterparty2));

        List<Counterparty> founded = counterpartyRepository.findAll();
        assertTrue(founded.size() > 0);
        assertEquals(mustBeFounded, founded);
    }

    /**
     * Тестируется сохранение контрагенетов
     */
    @Test
    void saveCounterparty() {
        Counterparty saved = counterpartyRepository.save(counterparty2);
        assertEquals(saved, counterpartyRepository.getById(saved.getId()));
    }

    /**
     * Тестируется удаление контрагентов
     */
    @Test
    void deleteCounterparty() {
        Counterparty saved = counterpartyRepository.save(counterparty2);
        counterpartyRepository.delete(saved);
        Optional<Counterparty> founded = counterpartyRepository.findById(saved.getId());
        assertFalse(founded.isPresent());
    }

    /**
     * Тестируется поиск но наименованию
     */
    @Test
    void findByName() {
        Counterparty saved = counterpartyRepository.save(counterparty);
        Optional<Counterparty> founded = counterpartyRepository.findByName(saved.getName());
        assertTrue(founded.isPresent());
        founded.ifPresent(value -> assertEquals(value, saved));

    }

    /**
     * Тестируется поиск по БИКу и Номеру счета
     */
    @Test
    void findAllByBikBankAndAccountNumber() {
        List<Counterparty> mustBeFounded = new ArrayList<>();
        Counterparty saved = counterpartyRepository.save(counterparty2);
        mustBeFounded.add(saved);
        List<Counterparty> founded = counterpartyRepository.findAllByBikBankAndAccountNumber(saved.getBikBank(), saved.getAccountNumber());
        assertTrue(founded.size() > 0);
        assertEquals(mustBeFounded, founded);
    }
}
