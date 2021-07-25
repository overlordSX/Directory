package ru.solovyev.counterpartyDirectory.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;
import ru.solovyev.counterpartyDirectory.validator.CounterpartyValidator;

import java.util.List;

/**
 * Контроллер для работы со справочником
 * (создание/изменение/удаление записей в справочнике)
 */
@Controller
@RequestMapping("/counterparties")
public class CounterpartyController {

    private final CounterpartyService counterpartyService;
    private final CounterpartyValidator counterpartyValidator;

    @Autowired
    public CounterpartyController(CounterpartyService counterpartyService, CounterpartyValidator counterpartyValidator) {
        this.counterpartyService = counterpartyService;
        this.counterpartyValidator = counterpartyValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(counterpartyValidator);
    }

    @GetMapping()
    @ApiOperation(value = "Запрос формы для отображения всех контрагентов",
            notes = "Будут отображены, все найденные контрагенты + на этой же странице форма для поиска")
    public String findAll(Model model) {
        List<Counterparty> counterparties = counterpartyService.findAll();
        model.addAttribute("counterparties", counterparties);
        return "counterparties/directory";
    }

    @GetMapping("/new")
    @ApiOperation(value = "Запрос формы для добавления нового контрагента")
    public String createCounterpartyForm(@ModelAttribute("counterparty") Counterparty counterparty) {
        return "counterparties/new";
    }

    @PostMapping("/new")
    @ApiOperation(value = "Операция добавления контрагента",
            notes = "Если данные введенные в форму, пройдут валидацию, " +
                    "то контрагент будет добавлен")
    public String createCounterparty(@Validated @ModelAttribute("counterparty") Counterparty counterparty, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "counterparties/new";
        }
        counterpartyService.saveCounterparty(counterparty);
        return "redirect:/counterparties";
    }

    @GetMapping("/{id}/edit")
    @ApiOperation(value = "Запрос формы для редактирования контрагента")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("counterparty", counterpartyService.findById(id));
        return "counterparties/edit";
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Операция редактирования контрагента",
            notes = "Если данные введенные в форму, пройдут валидацию, " +
                    "то измененные данные будут сохранены")
    public String update(@Validated @ModelAttribute("counterparty") Counterparty counterparty, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "counterparties/edit";
        }

        counterpartyService.saveCounterparty(counterparty);
        return "redirect:/counterparties";
    }

    @GetMapping("/{id}/delete")
    @ApiOperation(value = "Операция удаления контрагента")
    public String delete(@PathVariable("id") Long id) {
        counterpartyService.deleteCounterparty(counterpartyService.findById(id));
        return "redirect:/counterparties";
    }

}
