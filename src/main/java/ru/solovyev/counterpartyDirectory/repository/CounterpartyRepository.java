package ru.solovyev.counterpartyDirectory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;

import java.util.Optional;

/**
 * Интерфейс для работы с базой данных
 */
@Repository
public interface CounterpartyRepository extends JpaRepository<Counterparty, Long> {

    /**
     * Абстрактный метод поиска по наименованию
     * @param name наименование иского
     * @return найденный контрагент
     */
    Optional<Counterparty> findByName(String name);
}
