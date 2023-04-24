package com.example.demo.controllers;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }

    @PostMapping
    public UsuarioDTO guardarUsuario(@Valid @RequestBody UsuarioDTO usuario){
        return  usuarioService.guardarUsuario(usuario);
    }

    @GetMapping(path = "/{id}")
    public Optional<UsuarioDTO> obtenerPorId(@PathVariable("id") Long id){
        return usuarioService.obtenerPorId(id);
    }

    @GetMapping(path = "/query")
    public List<UsuarioDTO> obtenerPorPrioridad(@RequestParam("prioridad") Integer prioridad){
        return usuarioService.obtenerUsuariosPorPrioridad(prioridad);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarPorId(@PathVariable("id") Long id){
        if(usuarioService.eliminarUsuario(id)){
            return "se elimino el usuario con id: " + id;
        }else{
            return "no se pudo eliminar el usuario con id: " + id;
        }
    }

}
