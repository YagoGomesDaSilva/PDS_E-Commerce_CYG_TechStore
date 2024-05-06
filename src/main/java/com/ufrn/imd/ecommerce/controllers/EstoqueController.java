package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.models.entidades.Estoque;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.services.EstoqueService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PutMapping("/{anuncianteId}")
    public Estoque atualizarEstoque(@PathVariable Long anuncianteId, @RequestBody Produto produto, @RequestParam int quantidade) {
        return estoqueService.updateEstoque(anuncianteId, produto, quantidade);
    }

    @PostMapping("/{anuncianteId}")
    public Estoque criarEstoque(@PathVariable Long anuncianteId, @RequestBody Produto produto) {
        return estoqueService.createEstoque(anuncianteId, produto);
    }

    @DeleteMapping("/{anuncianteId}")
    public Estoque deletarEstoque(@PathVariable Long anuncianteId, @RequestBody Produto produto) {
        return estoqueService.deleteEstoque(anuncianteId, produto);
    }

    @GetMapping("/{anuncianteId}")
    public Estoque encontrarEstoque(@PathVariable Long anuncianteId, @RequestBody Produto produto) {
        return estoqueService.findEstoque(anuncianteId, produto);
    }
}
