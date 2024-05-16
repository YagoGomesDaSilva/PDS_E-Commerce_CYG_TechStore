package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
import com.ufrn.imd.ecommerce.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{idUsuario}")
    public UsuarioConcreto getUser(@PathVariable Long idUsuario){
        try{
            Optional<UsuarioConcreto> usuario = Optional.of(usuarioService.findUsuario(idUsuario));

            if (usuario.isPresent()){
                return usuario.get();
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioConcreto createUser(@RequestBody UsuarioConcreto usuario){
        try {
            return usuarioService.createUsuario(usuario);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioConcreto updateUser(@RequestBody UsuarioConcreto usuario) {
        try {
            return usuarioService.createUsuario(usuario);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
