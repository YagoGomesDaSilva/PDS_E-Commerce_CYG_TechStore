package com.ufrn.imd.ecommerce.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsuarioController {

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
