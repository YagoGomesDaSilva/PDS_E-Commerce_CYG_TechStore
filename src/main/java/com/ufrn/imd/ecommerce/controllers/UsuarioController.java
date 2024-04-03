package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.models.Usuario;
import com.ufrn.imd.ecommerce.services.UsuarioService;
import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getUser(@RequestParam(value = "idUsuario") Long idUsuario){
        try{
            Usuario usuario = usuarioService.findUsuario(idUsuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "user")
    public ResponseEntity<?> createUser(@RequestBody Usuario usuario){
        try {
            usuarioService.createUsuario(usuario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}