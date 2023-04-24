package com.example.demo.mapper;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.models.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
@MapperConfig(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(source="nombreDeUsuario", target = "nombre")
    UsuarioEntity dtoToEntity(UsuarioDTO usuarioDTO);

    @Mapping(target="nombreDeUsuario", source = "nombre")
    UsuarioDTO entityToDto(UsuarioEntity usuarioEntity);
}