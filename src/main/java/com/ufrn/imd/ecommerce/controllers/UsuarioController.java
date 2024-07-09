package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.error.exceptions.CreditoExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.DTO.CreditoDTO;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    private final ClienteService clienteService;

    public UsuarioController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/credit")
    public void addCredito(@RequestBody CreditoDTO creditoDTO){
        try {
            Usuario usuario = clienteService.findUsuario(creditoDTO.getIdUsuario());
            clienteService.addCredito(usuario, creditoDTO.getCredito());
        } catch (UsuarioExCustom e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (CreditoExCustom e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/credit/{idUsuario}")
    public double findCredito(@PathVariable Long idUsuario){
        try {
            Usuario usuario = clienteService.findUsuario(idUsuario);
            return usuario.getCredito();
        } catch (UsuarioExCustom e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

//    private final UsuarioService usuarioService;
//    public UsuarioController(UsuarioService usuarioService) {
//        this.usuarioService = usuarioService;
//    }
//
//    @GetMapping("/{idUsuario}")
//    public UsuarioConcreto getUser(@PathVariable Long idUsuario){
//        try{
//            Optional<UsuarioConcreto> usuario = Optional.of(usuarioService.findUsuario(idUsuario));
//
//            if (usuario.isPresent()){
//                return usuario.get();
//            }
//            else {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//            }
//
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public UsuarioConcreto createUser(@RequestBody UsuarioConcreto usuario){
//        try {
//            return usuarioService.createUsuario(usuario);
//        } catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public UsuarioConcreto updateUser(@RequestBody UsuarioConcreto usuario) {
//        try {
//            return usuarioService.createUsuario(usuario);
//        } catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//    }

}
