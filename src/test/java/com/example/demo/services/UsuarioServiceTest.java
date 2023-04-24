package com.example.demo.services;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.models.UsuarioEntity;
import com.example.demo.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioDTO usuario1,usuario2,usuario3,usuario4,usuario5,usuario6;

    private UsuarioEntity usuario1Entity, usuario2Entity,usuario3Entity,usuario4Entity,usuario5Entity,usuario6Entity;

    private  List<UsuarioDTO> listaDeUsuariosCompleta;

    private  List<UsuarioDTO> listaDeUsuariosFiltrada;

    private  List<UsuarioEntity> listaDeUsuariosCompletaEntity;

    private  List<UsuarioEntity> listaDeUsuariosFiltradaEntity;
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


        usuario1Entity = new UsuarioEntity();
        usuario1Entity.setId(1l);
        usuario1Entity.setNombre("Pablo");
        usuario1Entity.setEmail("pablo@hot.com");
        usuario1Entity.setPrioridad(1);

        usuario2Entity = new UsuarioEntity();
        usuario2Entity.setId(2l);
        usuario2Entity.setNombre("Diego");
        usuario2Entity.setEmail("diego@hot.com");
        usuario2Entity.setPrioridad(3);

        usuario3Entity =new UsuarioEntity();
        usuario3Entity.setId(3l);
        usuario3Entity.setNombre("Luciano");
        usuario3Entity.setEmail("lu@hot.com");
        usuario3Entity.setPrioridad(5);

        usuario4Entity = new UsuarioEntity();
        usuario4Entity.setId(4l);
        usuario4Entity.setNombre("Ale");
        usuario4Entity.setEmail("ale@hot.com");
        usuario4Entity.setPrioridad(5);

        usuario5Entity = new UsuarioEntity();
        usuario5Entity.setId(5l);
        usuario5Entity.setNombre("Mirtis");
        usuario5Entity.setEmail("mirta@hot.com");
        usuario5Entity.setPrioridad(5);

        usuario6Entity = new UsuarioEntity();
        usuario6Entity.setId(6l);
        usuario6Entity.setNombre("Edy");
        usuario6Entity.setEmail("edgardo@hot.com");
        usuario6Entity.setPrioridad(1);

        listaDeUsuariosCompletaEntity = new ArrayList<>();
        listaDeUsuariosCompletaEntity.add(usuario1Entity);
        listaDeUsuariosCompletaEntity.add(usuario2Entity);
        listaDeUsuariosCompletaEntity.add(usuario3Entity);
        listaDeUsuariosCompletaEntity.add(usuario4Entity);
        listaDeUsuariosCompletaEntity.add(usuario5Entity);
        listaDeUsuariosCompletaEntity.add(usuario6Entity);

        listaDeUsuariosFiltradaEntity= new ArrayList<>();
        listaDeUsuariosFiltradaEntity.add(usuario3Entity);
        listaDeUsuariosFiltradaEntity.add(usuario4Entity);
        listaDeUsuariosFiltradaEntity.add(usuario5Entity);
    }

    @Test
    void obtenerUsuarios() {
        Mockito.when(usuarioRepository.findAll()).thenReturn( listaDeUsuariosCompletaEntity);
        List<UsuarioDTO> response = usuarioService.obtenerUsuarios();
        Assertions.assertArrayEquals(response.toArray(), listaDeUsuariosCompleta.toArray());
    }

    @Test
    void guardarUsuario() {
        Mockito.when(usuarioRepository.save(Mockito.any(UsuarioEntity.class))).thenReturn(usuario1Entity);
        UsuarioDTO response = usuarioService.guardarUsuario(usuario1);
        Assertions.assertEquals(response, usuario1);
    }

    @Test
    void obtenerPorId() {
        Mockito.when(usuarioRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(usuario2Entity));
        Optional<UsuarioDTO> response = usuarioService.obtenerPorId(2l);
        Assertions.assertEquals(response.get(), usuario2);
    }

    @Test
    void obtenerUsuariosPorPrioridad() {
        Mockito.when(usuarioRepository.findByPrioridad(Mockito.any(Integer.class))).thenReturn(listaDeUsuariosFiltradaEntity);
        List<UsuarioDTO> response = usuarioService.obtenerUsuariosPorPrioridad(5);
        Assertions.assertArrayEquals(response.toArray(), listaDeUsuariosFiltrada.toArray());
    }

    @Test
    void eliminarUsuario() {
        boolean response = usuarioService.eliminarUsuario(5l);
        Assertions.assertEquals(response, true);
        Mockito.doThrow(IllegalArgumentException.class).when(usuarioRepository).deleteById(Mockito.any(Long.class));
        response = usuarioService.eliminarUsuario(5l);
        Assertions.assertEquals(response, false);
    }
}