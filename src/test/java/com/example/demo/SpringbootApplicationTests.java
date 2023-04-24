package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringbootApplicationTests {

	String listaConUsuarioEliminado = "[{\"id\":1,\"name\":\"Mica\",\"email\":\"micaela@hot.com\",\"priority\":5},{\"id\":2,\"name\":\"Edy\",\"email\":\"edgardo@hot.com\",\"priority\":3},{\"id\":3,\"name\":\"Pablo\",\"email\":\"pablo@hot.com\",\"priority\":1}]";
	String listaFiltrada = "[{\"id\":1,\"name\":\"Mica\",\"email\":\"micaela@hot.com\",\"priority\":5},{\"id\":4,\"name\":\"Diego\",\"email\":\"diego@hot.com\",\"priority\":5}]";
	String listaCompleta = "[{\"id\":1,\"name\":\"Mica\",\"email\":\"micaela@hot.com\",\"priority\":5},{\"id\":2,\"name\":\"Edy\",\"email\":\"edgardo@hot.com\",\"priority\":3},{\"id\":3,\"name\":\"Pablo\",\"email\":\"pablo@hot.com\",\"priority\":1},{\"id\":4,\"name\":\"Diego\",\"email\":\"diego@hot.com\",\"priority\":5}]";
	String usuario1 = "{\"id\" : 1,\"name\" : \"Mica\",\"email\" : \"micaela@hot.com\",\"priority\" : 5}";
	String usuario2 = "{\"id\" : 2,\"name\" : \"Edy\",\"email\" : \"edgardo@hot.com\",\"priority\" : 3}";
	String usuario3 = "{\"id\" : 3,\"name\" : \"Pablo\",\"email\" : \"pablo@hot.com\",\"priority\" : 1}";
	String usuario4 = "{\"id\" : 4,\"name\" : \"Diego\",\"email\" : \"diego@hot.com\",\"priority\" : 5}";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		mockMvc.perform(post("/usuario")
						.contentType("application/json")
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
						.content(usuario1))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(usuario1));

		mockMvc.perform(post("/usuario")
						.contentType("application/json")
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
						.content(usuario2))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(usuario2));

		mockMvc.perform(post("/usuario")
						.contentType("application/json")
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
						.content(usuario3))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(usuario3));

		mockMvc.perform(post("/usuario")
						.contentType("application/json")
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
						.content(usuario4))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(usuario4));

		mockMvc.perform(get("/usuario")
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(listaCompleta));


		mockMvc.perform(get(String.format("/usuario/%s", 4))
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(usuario4));

		mockMvc.perform(get(String.format("/usuario/%s", 2))
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(usuario2));

		mockMvc.perform(get(String.format("/usuario/query?prioridad=%s", 5))
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(listaFiltrada));

		mockMvc.perform(delete(String.format("/usuario/%s", 4))
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().string("se elimino el usuario con id: 4"));

		mockMvc.perform(get("/usuario")
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().json(listaConUsuarioEliminado));
	}

}
