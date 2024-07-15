package com.ForoHub.ForoHub.controller;

import com.ForoHub.ForoHub.domain.usuarios.DatosAutenticacionUsuario;

import com.ForoHub.ForoHub.domain.usuarios.Usuario;
import com.ForoHub.ForoHub.infra.security.DatosJWTToken;
import com.ForoHub.ForoHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public String usurioEnUso;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacion){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacion.login(), datosAutenticacion.clave());
        System.out.println(authToken);
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        System.out.println(usuarioAutenticado);
        usurioEnUso = usuarioAutenticado.getName();
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        System.out.println(JWTtoken);
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}
