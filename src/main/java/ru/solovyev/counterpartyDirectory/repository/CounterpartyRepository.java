package ru.solovyev.counterpartyDirectory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;

import java.util.List;
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

    /**
     * Абстрактный метод поиска по БИКу банка и номеру счета
     * @param bikBank БИК банка
     * @param accountNumber номер счета
     * @return список найденных контрагентов
     */
    List<Counterparty> findAllByBikBankAndAccountNumber(String bikBank, String accountNumber);
}
