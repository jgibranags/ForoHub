package com.ForoHub.ForoHub.domain.topicos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DatosRegitroTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotBlank LocalDateTime fechaDeCreacion,
        @NotBlank String usuario
        ) {
}
