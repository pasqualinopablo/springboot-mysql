package com.example.demo.repositories;

import com.example.demo.models.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioEntity,Long> {
    public abstract List<UsuarioEntity> findByPrioridad(Integer prioridad);
}
