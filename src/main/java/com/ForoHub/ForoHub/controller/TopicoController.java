package com.ForoHub.ForoHub.controller;

import com.ForoHub.ForoHub.domain.topicos.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@ResponseBody
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity elimnarPost(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivar();;
        return ResponseEntity.ok("Registro con id" + id + " eliminado con exito");
    }

    @GetMapping
    public ResponseEntity<Page<DatosTopico>> listadoTopicos(@PageableDefault(size = 5, sort = "fechaDeCreacion")Pageable paginacion){
        return ResponseEntity.ok(topicoRepository.findByActivoTrue(paginacion).map(DatosTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosTopico> retornarPorId(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosTopico(topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion(), topico.getUsuario());
        return ResponseEntity.ok(datosTopico);
    }

    @PostMapping
    public ResponseEntity agregarTopico(@RequestBody @Valid DatosRegitroTopico datosRegitroTopico, UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicoRepository.save(new Topico(datosRegitroTopico));
        DatosTopico datosRespuestaTopico= new DatosTopico(topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion(), topico.getUsuario());
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Topico medico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        medico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok().build();
    }

}
