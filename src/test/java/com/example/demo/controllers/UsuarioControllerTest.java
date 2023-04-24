package com.example.demo.controllers;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.models.UsuarioEntity;
import com.example.demo.services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MimeTypeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsuarioService usuarioService;
    @Autowired
    private ObjectMapper objectMapper;
    private UsuarioDTO usuario1,usuario2,usuario3,usuario4,usuario5,usuario6;

    private  List<UsuarioDTO> listaDeUsuariosCompleta;

    private  List<UsuarioDTO> listaDeUsuariosFiltrada;

    private String usuario;

    private  String listaDeUsuarios;

    @BeforeEach
    void setUp() {

        usuario1 = new UsuarioDTO();
        usuario1.setId(1l);
        usuario1.setNombreDeUsuario("Pablo");
        usuario1.setEmail("pablo@hot.com");
        usuario1.setPrioridad(1);

        usuario2 = new UsuarioDTO();
        usuario2.setId(2l);
        usuario2.setNombreDeUsuario("Diego");
        usuario2.setEmail("diego@hot.com");
        usuario2.setPrioridad(3);

        usuario3 =new UsuarioDTO();
        usuario3.setId(3l);
        usuario3.setNombreDeUsuario("Luciano");
        usuario3.setEmail("lu@hot.com");
        usuario3.setPrioridad(5);

        usuario4 = new UsuarioDTO();
        usuario4.setId(4l);
        usuario4.setNombreDeUsuario("Ale");
        usuario4.setEmail("ale@hot.com");
        usuario4.setPrioridad(5);

        usuario5 = new UsuarioDTO();
        usuario5.setId(5l);
        usuario5.setNombreDeUsuario("Mirtis");
        usuario5.setEmail("mirta@hot.com");
        usuario5.setPrioridad(5);

        usuario6 = new UsuarioDTO();
        usuario6.setId(6l);
        usuario6.setNombreDeUsuario("Edy");
        usuario6.setEmail("edgardo@hot.com");
        usuario6.setPrioridad(1);

        listaDeUsuariosCompleta = new ArrayList<>();
        listaDeUsuariosCompleta.add(usuario1);
        listaDeUsuariosCompleta.add(usuario2);
        listaDeUsuariosCompleta.add(usuario3);
        listaDeUsuariosCompleta.add(usuario4);
        listaDeUsuariosCompleta.add(usuario5);
        listaDeUsuariosCompleta.add(usuario6);

        listaDeUsuariosFiltrada= new ArrayList<>();
        listaDeUsuariosFiltrada.add(usuario3);
        listaDeUsuariosFiltrada.add(usuario4);
        listaDeUsuariosFiltrada.add(usuario5);
    }

    @Test
    void obtenerUsuarios() throws Exception{
        Mockito.when(usuarioService.obtenerUsuarios()).thenReturn( listaDeUsuariosCompleta);
        mockMvc.perform(get("/usuario")
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(toJSON(listaDeUsuariosCompleta)));
    }

    @Test
    void guardarUsuario() throws Exception{
        Mockito.when(usuarioService.guardarUsuario(Mockito.any(UsuarioDTO.class))).thenReturn(usuario2);
        MvcResult response =mockMvc.perform(post("/usuario")
                        .contentType("application/json")
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .content(toJSON(usuario2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(toJSON(usuario2)))
                .andReturn();

        Assertions.assertEquals(toObject(response.getResponse().getContentAsString(), UsuarioDTO.class ) , usuario2);
    }


    @Test
    void obtenerPorId() throws Exception {
        Mockito.when(usuarioService.obtenerPorId(Mockito.any(Long.class))).thenReturn(Optional.of(usuario3));
        mockMvc.perform(get(String.format("/usuario/%s", 1))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(toJSON(usuario3)));

    }

    @Test
    void obtenerUsuariosPorPrioridad() throws Exception {
        Mockito.when(usuarioService.obtenerUsuariosPorPrioridad(Mockito.any(Integer.class))).thenReturn(listaDeUsuariosFiltrada);
        MvcResult response = mockMvc.perform(get(String.format("/usuario/query?prioridad=%s", 5))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(toJSON(listaDeUsuariosFiltrada)))
                .andReturn();

        List<UsuarioEntity> lista = objectMapper.readValue(response.getResponse().getContentAsString(), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, UsuarioDTO.class) );

        Assertions.assertArrayEquals( lista.toArray(), listaDeUsuariosFiltrada.toArray());
    }

    @Test
    void eliminarUsuario() throws Exception {
        mockMvc.perform(delete(String.format("/usuario/%s", 5))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("no se pudo eliminar el usuario con id: " + 5));

        Mockito.when(usuarioService.eliminarUsuario(Mockito.any(Long.class))).thenReturn(true);

        mockMvc.perform(delete(String.format("/usuario/%s", 5))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("se elimino el usuario con id: " + 5));

    }

    private String toJSON(Object objeto)  {
        String resultado;
        try {
            resultado= objectMapper.writeValueAsString(objeto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
    private Object toObject(String string, Class model) {
        Object resultado;
        try {
            resultado= objectMapper.readValue(string,model);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

}