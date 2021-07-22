package ru.solovyev.counterpartyDirectory.controller;

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
        binder.addValidators(counterpartyValidator);
    }

    @GetMapping()
    public String findAll(Model model) {
        List<Counterparty> counterparties = counterpartyService.findAll();
        model.addAttribute("counterparties", counterparties);
        return "counterparties/directory";
    }

    @GetMapping("/new")
    public String createCounterpartyForm(@ModelAttribute("counterparty") Counterparty counterparty) {
        return "counterparties/new";
    }

    @PostMapping("/new")
    public String createCounterparty(@Validated @ModelAttribute("counterparty") Counterparty counterparty, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "counterparties/new";
        }
        counterpartyService.saveCounterparty(counterparty);
        return "redirect:/counterparties";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("counterparty", counterpartyService.findById(id));
        return "counterparties/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("counterparty") Counterparty counterparty, @PathVariable("id") Long id) {
        counterpartyService.saveCounterparty(counterparty);
        return "redirect:/counterparties";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        counterpartyService.deleteCounterparty(counterpartyService.findById(id));
        return "redirect:/counterparties";
    }

}
