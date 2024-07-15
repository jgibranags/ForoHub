package com.ForoHub.ForoHub.domain.topicos;

import com.ForoHub.ForoHub.controller.AutenticacionController;
import com.ForoHub.ForoHub.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String mensaje;

    private LocalDateTime fechaDeCreacion;

    private boolean activo;

    private String usuario;

    public Topico(String titulo, String mensaje){
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaDeCreacion = LocalDateTime.now();
        this.activo = true;
        AutenticacionController usuario = new AutenticacionController();
        this.usuario = usuario.usurioEnUso;

    }

    public Topico(DatosRegitroTopico datosRegitroTopico) {
        this.titulo = datosRegitroTopico.titulo();
        this.mensaje = datosRegitroTopico.mensaje();
        this.activo = true;
        this.fechaDeCreacion = LocalDateTime.now();
        AutenticacionController usuario = new AutenticacionController();
        this.usuario = usuario.usurioEnUso;
    }

    public void desactivar(){
        this.activo = false;
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if(datosActualizarTopico.titulo() != null){
            this.titulo = datosActualizarTopico.titulo();
        }
        if(datosActualizarTopico.mensaje() != null){
            this.mensaje = datosActualizarTopico.mensaje();
        }
        this.fechaDeCreacion = LocalDateTime.now();
    }
}
