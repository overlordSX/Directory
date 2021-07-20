package ru.solovyev.counterpartyDirectory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;

@Repository
public interface CounterpartyRepository extends JpaRepository<Counterparty, Long> {
}