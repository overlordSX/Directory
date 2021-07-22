package ru.solovyev.counterpartyDirectory.entity;

import lombok.*;


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
    private Long id;


    /**
     * Наименование.
     * Проверять что не больше 20 ти символов.
     */
    @Column(name = "name")
    private String name;
    /**
     * ИНН контрагента
     */
    @Column(name = "inn")
    private String inn;
    /**
     * КПП контрагента
     */
    @Column(name = "kpp")
    private String kpp;
    /**
     * БИК банка
     */
    @Column(name = "bikBank")
    private String bikBank;
    /**
     * Номер счета
     */
    @Column(name = "accountNumber")
    private String accountNumber;


//TODO SOLVED task-1 нужно решить использовать ломбок или нет
//TODO SOLVED task-2 после понять как пользоваться h2 базой данных
//TODO SOLVED task-3 разобраться с Repository
//TODO SOLVED task-4 разобраться с Service
//TODO SOLVED task-5 разобраться с Controller


}

