package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name= "USUARIO")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String nombre;

    private String email;

    private Integer prioridad;
}
