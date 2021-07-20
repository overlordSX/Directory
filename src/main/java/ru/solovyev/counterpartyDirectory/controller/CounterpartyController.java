package ru.solovyev.counterpartyDirectory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;

import java.util.List;

@Controller
public class CounterpartyController {
    CounterpartyService counterpartyService;

    @GetMapping
    public String findAll(Model model) {
        List<Counterparty> counterparties = counterpartyService.findAll();
        model.addAttribute("counterparties", counterparties);
        return "counterparties";
    }
}
