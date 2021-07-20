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
@Table(name = "counterpaties")
public class Counterparty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    /**
     * Наименование.
     * Проверять что не больше 20 ти символов.
     */
    @Column(name = "Наименование")
    String name;
    /**
     * ИНН контрагента
     */
    @Column(name = "ИНН")
    String inn;
    /**
     * КПП контрагента
     */
    @Column(name = "КПП")
    String kpp;
    /**
     * БИК банка
     */
    @Column(name = "БИК банка")
    String bikBank;
    /**
     * Номер счета
     */
    @Column(name = "Номер счета")
    String accountNumber;


//TODO task-1 нужно решить использовать ломбок или нет
//TODO task-2 после понять как пользоваться h2 базой данных
//TODO task-3 разобраться с Repository
//TODO task-4 разобраться с Service
//TODO task-5 разобраться с Controller


}

