package ru.solovyev.counterpartyDirectory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;

import java.util.List;

@Controller
@RequestMapping("/counterparties")
public class CounterpartyController {

    final CounterpartyService counterpartyService;

    @Autowired
    public CounterpartyController(CounterpartyService counterpartyService) {
        this.counterpartyService = counterpartyService;
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
    public String createCounterparty(@ModelAttribute("counterparty") Counterparty counterparty) {
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
