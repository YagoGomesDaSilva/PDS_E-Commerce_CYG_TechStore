package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.services.AnuncianteService;
import com.ufrn.imd.ecommerce.services.UsuarioService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/anunciante")
public class AnuncianteController {

    private final AnuncianteService anuncianteService;
    private final UsuarioService usuarioService;

    public AnuncianteController(AnuncianteService anuncianteService, UsuarioService usuarioService) {
        this.anuncianteService = anuncianteService;
        this.usuarioService = usuarioService;
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Anunciante create(@RequestBody Anunciante anunciante, HttpServletRequest request) {
//        try {
//            Cliente usuario = clienteService.findUsuarioByToken(request);
//            anuncianteService.createUsuario(anunciante, usuario);
//            return anunciante;
//        } catch (UsuarioExCustom err) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//    }
}



