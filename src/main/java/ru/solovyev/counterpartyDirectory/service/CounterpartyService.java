package ru.solovyev.counterpartyDirectory.service;

import lombok.Setter;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.repository.CounterpartyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CounterpartyService {

    private final CounterpartyRepository counterpartyRepository;

    @Autowired
    public CounterpartyService(CounterpartyRepository counterpartyRepository) {
        this.counterpartyRepository = counterpartyRepository;
    }

    public Counterparty findById(Long id) {
        return counterpartyRepository.getById(id);
    }

    public List<Counterparty> findAll() {
        return counterpartyRepository.findAll();
    }

    public Counterparty saveCounterparty(Counterparty counterparty) {
        return counterpartyRepository.save(counterparty);
    }

    public void deleteCounterparty(Counterparty counterparty) {
        counterpartyRepository.delete(counterparty);
    }

    public Optional<Counterparty> findByName(String name) {
        return counterpartyRepository.findByName(name);
    }

}
