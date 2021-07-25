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

    /**
     * Уникальный идентификатор контрагента в базе данных
     */
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
     * Состоит из 10 или 12 цифр
     */
    @Column(name = "inn")
    private String inn;
    /**
     * КПП контрагента
     * Состоит из 9 цифр
     */
    @Column(name = "kpp")
    private String kpp;
    /**
     * БИК банка
     * Состоит из 9 цифр
     */
    @Column(name = "bikBank")
    private String bikBank;
    /**
     * Номер счета
     * Состоит из 20 цифр
     */
    @Column(name = "accountNumber")
    private String accountNumber;
}

