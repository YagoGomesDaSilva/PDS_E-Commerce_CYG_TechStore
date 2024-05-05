package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.services.AnuncianteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/anunciante")
public class AnuncianteController {

    private final AnuncianteService anuncianteService;

    public AnuncianteController(AnuncianteService anuncianteService) {
        this.anuncianteService = anuncianteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Anunciante create(@RequestBody Anunciante anunciante) {
        anuncianteService.createAnunciante(anunciante);
        return anunciante;
    }
}



