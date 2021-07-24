package ru.solovyev.counterpartyDirectory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для поиска контрагентов в базе
 */
@Controller
@RequestMapping("/counterparties")
public class SearchController {

    final CounterpartyService counterpartyService;

    @Autowired
    public SearchController(CounterpartyService counterpartyService) {
        this.counterpartyService = counterpartyService;
    }


    @GetMapping("/search/name")
    public String searchByName(Model model, @RequestParam String name) {
        Optional<Counterparty> found = counterpartyService.findByName(name);
        if (found.isPresent()) {
            model.addAttribute("founded", true);
            model.addAttribute("result", found);
        } else {
            model.addAttribute("founded", false);
        }
        return "counterparties/searchByName";
    }

    @GetMapping("/search/bikAndAccount")
    public String searchByBikAndAccount(Model model, @RequestParam String bikBank, @RequestParam String accountNumber) {
        List<Counterparty> foundList = counterpartyService.findAllByBikBankAndAccountNumber(bikBank, accountNumber);
        if (!foundList.isEmpty()) {
            model.addAttribute("founded", true);
            model.addAttribute("result", foundList);
        } else {
            model.addAttribute("founded", false);
        }
        return "counterparties/searchByBikAndAccountNumber";
    }
}
