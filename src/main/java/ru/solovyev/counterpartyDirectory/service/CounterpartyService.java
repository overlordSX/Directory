package ru.solovyev.counterpartyDirectory.service;

import lombok.Setter;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.repository.CounterpartyRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с базой данных
 */
@Service
public class CounterpartyService {

    private final CounterpartyRepository counterpartyRepository;

    @Autowired
    public CounterpartyService(CounterpartyRepository counterpartyRepository) {
        this.counterpartyRepository = counterpartyRepository;
    }

    /**
     * Метод для поиска контрагента по id
     * @param id id искомого
     * @return контрагент
     */
    public Counterparty findById(Long id) {
        return counterpartyRepository.getById(id);
    }

    /**
     * Метод для сбора всех контрагентов
     * @return список всех контрагентов
     */
    public List<Counterparty> findAll() {
        return counterpartyRepository.findAll();
    }

    /**
     * Метод сохраняющий новогоконтрагента, либо изменяющий его данные
     * @param counterparty контрагент
     * @return полученный контрагент
     */
    public Counterparty saveCounterparty(Counterparty counterparty) {
        return counterpartyRepository.save(counterparty);
    }

    /**
     * Метод удаляющий контрагента
     * @param counterparty контрагент
     */
    public void deleteCounterparty(Counterparty counterparty) {
        counterpartyRepository.delete(counterparty);
    }

    /**
     * Метод ищущий контрагента по имени
     * @param name имя искомого
     * @return контрагент
     */
    public Optional<Counterparty> findByName(String name) {
        return counterpartyRepository.findByName(name);
    }

    /**
     * Метод ищущий всех контрагентов с таким БИКом и номером счета
     * @param bikBank БИК банка
     * @param accountNumber Номер счета
     * @return список найденных контрагентов
     */
    public List<Counterparty> findAllByBikBankAndAccountNumber(String bikBank, String accountNumber) {
        return counterpartyRepository.findAllByBikBankAndAccountNumber(bikBank, accountNumber);
    }

}
