package com.example.demo.services;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.models.UsuarioEntity;
import com.example.demo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> obtenerUsuarios(){

        return ((List<UsuarioEntity>) usuarioRepository.findAll()).stream()
                .map(entity -> UsuarioMapper.INSTANCE.entityToDto(entity))
                .collect(Collectors.toList());
    }

    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO){
        return UsuarioMapper.INSTANCE.entityToDto(usuarioRepository.save(UsuarioMapper.INSTANCE.dtoToEntity(usuarioDTO)));
    }

    public Optional<UsuarioDTO> obtenerPorId(Long id){
        return usuarioRepository.findById(id)
                .map(entity -> UsuarioMapper.INSTANCE.entityToDto(entity));
    }

    public List<UsuarioDTO> obtenerUsuariosPorPrioridad(Integer prioridad){
        return usuarioRepository.findByPrioridad(prioridad).stream()
                .map(entity -> UsuarioMapper.INSTANCE.entityToDto(entity))
                .collect(Collectors.toList());
    }

    public boolean eliminarUsuario(Long id){
        try{
            usuarioRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
