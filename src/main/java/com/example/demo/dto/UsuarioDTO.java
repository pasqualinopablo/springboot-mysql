package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioDTO {
    @JsonProperty("id")
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 1, max = 30)
    @JsonProperty("name")
    private String nombreDeUsuario;

    @Email
    @NotNull
    @JsonProperty("email")
    private String email;

    @Max(9)
    @JsonProperty("priority")
    private Integer prioridad;
}
