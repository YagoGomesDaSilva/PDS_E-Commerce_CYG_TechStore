package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
import com.ufrn.imd.ecommerce.services.AnuncianteService;
import com.ufrn.imd.ecommerce.services.UsuarioService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Anunciante create(@RequestBody Anunciante anunciante, HttpServletRequest request) {
        UsuarioConcreto usuario = usuarioService.findUsuarioByToken(request);
        anuncianteService.createAnunciante(anunciante, usuario);
        return anunciante;
    }
}



