package ru.solovyev.counterpartyDirectory.validator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;

import java.util.stream.Stream;

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

        String name = counterparty.getName();
        if (name.isBlank()) {
            errors.rejectValue("name", "", "Наименование не должно быть пустым");
        }
        if (name.length() > 20) {
            errors.rejectValue("name", "", "Наименование должно быть до 20 символов");
        }
        if (counterpartyService.findAll().stream().anyMatch(c -> c.getName().equals(name))) {
            errors.rejectValue("name", "", "Контрагент с таким наименованием уже существует");
        }

        String inn = counterparty.getInn();
        if (inn.isBlank()) {
            errors.rejectValue("inn", "", "ИНН не должно быть пустым");
        }
        if ((inn.length() != 10) && (inn.length() != 12)) {
            errors.rejectValue("inn", "", "ИНН состоит из 10 или 12 символов");
        }


    }
}
