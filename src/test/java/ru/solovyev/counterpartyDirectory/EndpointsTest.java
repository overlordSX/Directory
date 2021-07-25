package ru.solovyev.counterpartyDirectory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.solovyev.counterpartyDirectory.controller.CounterpartyController;
import ru.solovyev.counterpartyDirectory.entity.Counterparty;
import ru.solovyev.counterpartyDirectory.service.CounterpartyService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EndpointsTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	CounterpartyService counterpartyService;






	@Test
	public void mainPageTest() throws Exception {
		mvc.perform(get("/counterparties")).andExpect(status().isOk());
	}


	@Test
	public void postValid() throws Exception {
		Counterparty counterparty = new Counterparty(
				null,
				"Сбербанк",
				"7707083893",
				"773601001",
				"044525225",
				"30301810800006003800");
		List<Counterparty> database = new ArrayList<>();

		doAnswer(i -> {
			database.add(counterparty);
			return null;
		}).when(counterpartyService).saveCounterparty(counterparty);

		mvc.perform(post("/counterparties/new")).andExpect(status().isOk());
	}


	@Test
	public void simpleTest() throws Exception {
		Counterparty counterparty = new Counterparty(
				null,
				"Сбербанк",
				"7707083893",
				"773601001",
				"044525225",
				"30301810800006003800");

		System.err.println(asJsonString(counterparty));
		mvc.perform(post("/counterparties/new")
				.contentType("application/json")
				.content(String.valueOf(counterparty)))
				.andExpect(status().isOk());


	}




	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	//TODO удалить
	/*@Test
	void contextLoads() {
	}*/

}
