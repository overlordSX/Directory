package ru.solovyev.counterpartyDirectory.entity;

import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Класс описывающий сущность контрагента
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "counterparties")
public class Counterparty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    /**
     * Наименование.
     * Проверять что не больше 20 ти символов.
     */
    @Column(name = "name")
    String name;
    /**
     * ИНН контрагента
     */
    @Column(name = "inn")
    String inn;
    /**
     * КПП контрагента
     */
    @Column(name = "kpp")
    String kpp;
    /**
     * БИК банка
     */
    @Column(name = "bikBank")
    String bikBank;
    /**
     * Номер счета
     */
    @Column(name = "accountNumber")
    String accountNumber;


//TODO task-1 нужно решить использовать ломбок или нет
//TODO task-2 после понять как пользоваться h2 базой данных
//TODO task-3 разобраться с Repository
//TODO task-4 разобраться с Service
//TODO task-5 разобраться с Controller


}

