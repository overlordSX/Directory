package ru.solovyev.counterpartyDirectory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EndpointsTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CounterpartyService counterpartyService;

    private Counterparty counterparty;
    private MultiValueMap<String, String> params;

    @BeforeEach
    public void init() {
        counterparty = new Counterparty(
                1L,
                "Сбербанк",
                "7707083893",
                "773601001",
                "044525225",
                "30301810800006003800");

        params = new LinkedMultiValueMap<>();

        params.add("name", counterparty.getName());
        params.add("inn", counterparty.getInn());
        params.add("kpp", counterparty.getKpp());
        params.add("bikBank", counterparty.getBikBank());
        params.add("accountNumber", counterparty.getAccountNumber());

    }


    @Test
    public void mainPageTest() throws Exception {
        mvc.perform(get("/counterparties"))
                .andExpect(status().isOk())
                .andExpect(view().name("counterparties/directory"));
    }

    @Test
    public void addNewCounterpartyPageTest() throws Exception {
        mvc.perform(get("/counterparties/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("counterparties/new"));
    }

    @Test
    public void addValidCounterparty() throws Exception {
        List<Counterparty> database = new ArrayList<>();

        doAnswer(i -> {
            database.add(counterparty);
            return null;
        }).when(counterpartyService).saveCounterparty(counterparty);

        mvc.perform(post("/counterparties/new").params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/counterparties"))
                .andExpect(redirectedUrl("/counterparties"));
    }

    @Test
    public void addInvalidCounterparty() throws Exception {
        List<Counterparty> database = new ArrayList<>();

        Counterparty invalid = counterparty;
        invalid.setName("   ");
        params.set("name", "   ");

        doAnswer(i -> {
            database.add(invalid);
            return null;
        }).when(counterpartyService).saveCounterparty(invalid);

        mvc.perform(post("/counterparties/new").params(params))
                .andExpect(status().isOk())
                .andExpect(view().name("counterparties/new"))
                .andReturn();
    }
}
