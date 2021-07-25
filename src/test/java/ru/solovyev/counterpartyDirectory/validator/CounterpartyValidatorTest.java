package ru.solovyev.counterpartyDirectory.validator;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.Errors;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Класс тестирующий работу валидатора
 */
@ExtendWith(SpringExtension.class)
public class CounterpartyValidatorTest {

    private final CounterpartyService counterpartyService = mock(CounterpartyService.class);

    private final CounterpartyValidator counterpartyValidator = new CounterpartyValidator(counterpartyService);

    public static final int ONE = 1;

    Counterparty validCounterparty;
    Errors errors = mock(Errors.class);

    /**
     * Изначально валидный контрагент
     */
    @BeforeEach
    public void initCounterparty() {
        validCounterparty = new Counterparty(
                1L,
                "Сбербанк",
                "7707083893",
                "773601001",
                "044525225",
                "30301810800006003800");
    }

    /**
     * Тест валидного контрагента
     */
    @Test
    public void AllFieldsValid() {
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, never()).rejectValue(any(), any(), any());
    }

    /**
     * Тест невалидного поля имени
     */
    @Test
    public void EmptyNameTest() {
        validCounterparty.setName("");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("name"), any(), any());
    }

    @Test
    public void WhitespaceBeforeOrAfterNameTest() {
        validCounterparty.setName("  d");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("name"), any(), any());
    }

    /**
     * По требованиям тз, имя не должно превышать 20 символов
     */
    @Test
    public void TooLongNameTest() {
        validCounterparty.setName("dddddddddddddddddddddddddddddddddddddddddddddddd");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("name"), any(), any());
    }

    @Test
    public void EmptyInnTest() {
        validCounterparty.setInn("");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("inn"), any(), any());
    }

    @Test
    public void NonDigitInnTest() {
        validCounterparty.setInn("dddd");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("inn"), any(), any());
    }

    @Test
    public void WrongLengthInnTest() {
        validCounterparty.setInn("123456");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("inn"), any(), any());
    }

    /**
     * Валидатору на проверку подается некорректный ИНН организации
     * (состоит из 10 цифр)
     */
    @Test
    public void IncorrectInnTest1() {
        validCounterparty.setInn("7743013902");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("inn"), any(), any());
    }

    /**
     * Валидатору на проверку подается некорректный ИНН физического лица
     * (состоит из 12 цифр)
     */
    @Test
    public void IncorrectInnTest2() {
        validCounterparty.setInn("774301390233");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("inn"), any(), any());
    }

    @Test
    public void EmptyKppTest() {
        validCounterparty.setKpp("");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("kpp"), any(), any());
    }

    @Test
    public void NonDigitKppTest() {
        validCounterparty.setKpp("ddd");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("kpp"), any(), any());
    }

    /**
     * КПП состоит из 9 цифр
     */
    @Test
    public void WrongLengthKppTest() {
        validCounterparty.setKpp("123456789512");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("kpp"), any(), any());
    }

    @Test
    public void EmptyBikTest() {
        validCounterparty.setBikBank("");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("bikBank"), any(), any());
    }

    @Test
    public void NonDigitBikTest() {
        validCounterparty.setBikBank("dddd ");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("bikBank"), any(), any());
    }

    /**
     * БИК состоит из 9 цифр
     */
    @Test
    public void WrongLengthBikTest() {
        validCounterparty.setBikBank("1236");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("bikBank"), any(), any());
    }


    @Test
    public void EmptyAccountNumberTest() {
        validCounterparty.setAccountNumber("");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("accountNumber"), any(), any());
    }

    @Test
    public void NonDigitAccountNumberTest() {
        validCounterparty.setAccountNumber("d d r3");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("accountNumber"), any(), any());
    }

    /**
     * Тест неправильного количества
     * Номер счета состоит из 20 цифр
     */
    @Test
    public void WrongLengthAccountNumberTest() {
        validCounterparty.setAccountNumber("111111111111111111111111111111111111111111111");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("accountNumber"), any(), any());
    }

    /**
     * Тестируется номер счета в паре с БИК-ом банка
     * если в БИК-е банка 7 и 8 разряд не 0, то проверяется счет
     * откртый в кредитной организации
     */
    @Test
    public void WrongAccountNumberTest1() {
        validCounterparty.setBikBank("123456781");
        validCounterparty.setAccountNumber("22222222222222222222");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("bikBank"), any(), any());
        verify(errors, times(ONE)).rejectValue(eq("accountNumber"), any(), any());
    }

    /**
     * Тестируется номер счета в паре с БИК-ом банка
     * если в БИК-е банка 7 и 8 разряд 0, то проверяется счет
     * откртый в РКЦ
     */
    @Test
    public void WrongAccountNumberTest2() {
        validCounterparty.setBikBank("044525000");
        validCounterparty.setAccountNumber("30101810400000000333");
        counterpartyValidator.validate(validCounterparty, errors);

        verify(errors, times(ONE)).rejectValue(eq("bikBank"), any(), any());
        verify(errors, times(ONE)).rejectValue(eq("accountNumber"), any(), any());
    }




}
