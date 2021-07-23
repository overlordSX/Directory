package ru.solovyev.counterpartyDirectory.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;

import java.util.Arrays;
import java.util.stream.IntStream;

@Component
public class CounterpartyValidator implements Validator {

    private final CounterpartyService counterpartyService;

    @Autowired
    public CounterpartyValidator(CounterpartyService counterpartyService) {
        this.counterpartyService = counterpartyService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Counterparty.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        //TODO Можно инкапсулировать в другой класс


        Counterparty counterparty = (Counterparty) target;

        nameCheck(errors, counterparty);

        innCheck(errors, counterparty);

        kppCheck(errors, counterparty);

        bikBankCheck(errors, counterparty);

        checkAccountNumber(errors, counterparty);

        //TODO написать javadoc к методам
    }

    private void checkAccountNumber(Errors errors, Counterparty counterparty) {

        String bikBank = counterparty.getBikBank();
        String accountNumber = counterparty.getAccountNumber();

        if (accountNumber.isBlank()) {
            errors.rejectValue("accountNumber", "", "Номер счета не должен быть пустым");
        } else {

            String regex = "\\d+";
            if (accountNumber.matches(regex)) {
                if (accountNumber.length() != 20) {
                    errors.rejectValue("accountNumber", "", "Номер счета состоит из 20 цифр");
                }

                if (bikBank.length() == 9) {
                    if (bikBank.charAt(6) == '0' && bikBank.charAt(7) == '0') {
                        int[] rankCoefficient = {7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1};
                        int[] accountNumberDigits = accountNumber.chars().map(x -> x - '0').toArray();

                        int[] zeroPlusFiveAndSixBik = {0, bikBank.charAt(4) - '0', bikBank.charAt(5) - '0'};
                        int[] forCheckAccountNumberDigits = IntStream.concat(Arrays.stream(zeroPlusFiveAndSixBik), Arrays.stream(accountNumberDigits))
                                .toArray();

                        int controlSum = 0;
                        for (int i = 0; i < forCheckAccountNumberDigits.length; i++) {
                            controlSum += forCheckAccountNumberDigits[i] * rankCoefficient[i];
                        }

                        controlSum = controlSum % 10;

                        if (controlSum != 0) {
                            errors.rejectValue("accountNumber", "", "Либо неправильно указан номер счета, открытый в РКЦ");
                            errors.rejectValue("bikBank", "", "Либо неправильно указан БИК банка");
                        }

                    } else {
                        int[] rankCoefficient = {7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1};
                        int[] accountNumberDigits = accountNumber.chars().map(x -> x - '0').toArray();

                        int[] lastThreeBik = {bikBank.charAt(6) - '0', bikBank.charAt(7) - '0', bikBank.charAt(8) - '0'};
                        int[] forCheckAccountNumberDigits = IntStream.concat(Arrays.stream(lastThreeBik), Arrays.stream(accountNumberDigits))
                                .toArray();

                        int controlSum = 0;
                        for (int i = 0; i < forCheckAccountNumberDigits.length; i++) {
                            controlSum += forCheckAccountNumberDigits[i] * rankCoefficient[i];
                        }

                        controlSum = controlSum % 10;

                        if (controlSum != 0) {
                            errors.rejectValue("accountNumber", "", "Либо неправильно указан номер счета, открытый в кредитной организации");
                            errors.rejectValue("bikBank", "", "Либо неправильно указан БИК банка");
                        }

                        //TODO удалить этот комментарий
                        //errors.rejectValue("accountNumber", "", " " + Arrays.toString(forCheckAccountNumberDigits));
                    }
                }

            } else {
                errors.rejectValue("accountNumber", "", "Номер счета состоит только из цифр");
            }
        }
    }

    private void bikBankCheck(Errors errors, Counterparty counterparty) {
        String bikBank = counterparty.getBikBank();

        if (bikBank.isBlank()) {
            errors.rejectValue("bikBank", "", "БИК банка не должно быть пустым");
        } else {

            String regex = "\\d+";
            if (bikBank.matches(regex)) {
                if (bikBank.length() != 9) {
                    errors.rejectValue("bikBank", "", "БИК банка состоит из 9 цифр");
                }
            } else {
                errors.rejectValue("bikBank", "", "БИК банка состоит только из цифр");
            }
        }
    }

    private void kppCheck(Errors errors, Counterparty counterparty) {
        String kpp = counterparty.getKpp();

        if (kpp.isBlank()) {
            errors.rejectValue("kpp", "", "КПП не должно быть пустым");
        } else {

            String regex = "\\d+";
            if (kpp.matches(regex)) {
                if (kpp.length() != 9) {
                    errors.rejectValue("kpp", "", "КПП состоит из 9 цифр");
                }
            } else {
                errors.rejectValue("kpp", "", "КПП состоит только из цифр");
            }
        }
    }

    private void innCheck(Errors errors, Counterparty counterparty) {
        String inn = counterparty.getInn();

        if (inn.isBlank()) {
            errors.rejectValue("inn", "", "ИНН не должно быть пустым");
        } else {

            String regex = "\\d+";
            if (inn.matches(regex)) {

                if ((inn.length() != 10) && (inn.length() != 12)) {
                    errors.rejectValue("inn", "", "ИНН состоит из 10 или 12 цифр");
                }

                if (inn.length() == 10) {
                    int[] rankCoefficient = {2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
                    int[] innDigits = inn.chars().map(x -> x - '0').toArray();
                    //int controlSum = IntStream.of(innDigits).sum();
                    int controlSum = 0;
                    for (int i = 0; i < inn.length(); i++) {
                        controlSum += innDigits[i] * rankCoefficient[i];
                    }

                    controlSum = controlSum % 11;
                    if (controlSum > 9)
                        controlSum = controlSum % 10;

                    if (innDigits[inn.length() - 1] != controlSum) {
                        //TODO убрать потом коэффицент контрольной суммы
                        errors.rejectValue("inn", "", "Укажите корректный ИНН организации " + controlSum);
                    }
                }

                //TODO 10 и 12 можно вынести в константы
                if (inn.length() == 12) {

                    int[] firstRankCoefficient = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
                    int[] secondRankCoefficient = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};

                    int[] innDigits = inn.chars().map(x -> x - '0').toArray();
                    //int controlSum = IntStream.of(innDigits).sum();
                    int firstControlSum = 0;
                    //по первым 11
                    for (int i = 0; i < inn.length() - 1; i++) {
                        firstControlSum += innDigits[i] * firstRankCoefficient[i];
                    }
                    int secondControlSum = 0;
                    for (int i = 0; i < inn.length(); i++) {
                        secondControlSum += innDigits[i] * secondRankCoefficient[i];
                    }

                    firstControlSum = firstControlSum % 11;
                    if (firstControlSum > 9)
                        firstControlSum = firstControlSum % 10;

                    secondControlSum = secondControlSum % 11;
                    if (secondControlSum > 9)
                        secondControlSum = secondControlSum % 10;


                    if (innDigits[inn.length() - 1] != secondControlSum || innDigits[inn.length() - 2] != firstControlSum) {
                        //TODO убрать потом коэффицент контрольной суммы
                        errors.rejectValue("inn", "", "Укажите корректный ИНН физ. лица " + firstControlSum + " " + secondControlSum);
                    }
                }
            } else {
                errors.rejectValue("inn", "", "ИНН состоит только из цифр");
            }
        }
    }

    private void nameCheck(Errors errors, Counterparty counterparty) {
        String name = counterparty.getName();
        if (name.isBlank()) {
            errors.rejectValue("name", "", "Наименование не должно быть пустым");
        } else {
            if (name.length() > 20) {
                errors.rejectValue("name", "", "Наименование должно быть до 20 символов");
            } else if (!name.strip().equals(name)) {
                errors.rejectValue("name", "", "Удалите пробелы из начала или из конца");
            } else{
                counterpartyService.findByName(name).ifPresent(c -> {
                    if (!c.getId().equals(counterparty.getId())) {
                        errors.rejectValue("name", "", "Контрагент с таким наименованием уже существует");
                    }
                });
            }
        }
    }
}
