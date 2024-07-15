package com.ForoHub.ForoHub.domain.topicos;


import java.time.LocalDateTime;

public record DatosTopico(
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        String usuario
) {

    public DatosTopico(Topico topico){
        this(topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion(), topico.getUsuario());
    }
}
