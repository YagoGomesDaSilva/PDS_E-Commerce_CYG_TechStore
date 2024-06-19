package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.models.entidades.Estoque;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.services.EstoqueService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

//    @PostMapping("/{anuncianteId}")
//    public Estoque createEstoque(@PathVariable Long anuncianteId, @RequestBody Produto produto) {
//        return estoqueService.createEstoque(anuncianteId, produto).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
//    }
//    @PutMapping("/{anuncianteId}")
//    public Estoque updateEstoque(@PathVariable Long anuncianteId, @RequestBody Produto produto, @RequestParam int quantidade) {
//        return estoqueService.updateEstoque(anuncianteId, produto, quantidade).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
//    }
//
//    @DeleteMapping("/{anuncianteId}")
//    public Estoque deleteEstoque(@PathVariable Long anuncianteId, @RequestBody Produto produto) {
//        return estoqueService.deleteEstoque(anuncianteId, produto).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
//    }
//
//    @GetMapping("/{anuncianteId}")
//    public Estoque findEstoque(@PathVariable Long anuncianteId, @RequestBody Produto produto) {
//        return estoqueService.findEstoque(anuncianteId, produto).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
//    }
}
