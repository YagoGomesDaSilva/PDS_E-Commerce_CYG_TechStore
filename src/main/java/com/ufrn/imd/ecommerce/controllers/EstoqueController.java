package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.EstoqueExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.ProdutoExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.DTO.EstoqueDTO;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Estoque;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.services.AnuncianteService;
import com.ufrn.imd.ecommerce.services.EstoqueService;
import com.ufrn.imd.ecommerce.services.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;
    private final AnuncianteService anuncianteService;
    private final ProdutoService produtoService;

    public EstoqueController(EstoqueService estoqueService, AnuncianteService anuncianteService, ProdutoService produtoService) {
        this.estoqueService = estoqueService;
        this.anuncianteService = anuncianteService;
        this.produtoService = produtoService;
    }

    @PutMapping("/{idAnunciante}")
    public Estoque updateEstoque(@PathVariable Long idAnunciante, @RequestBody EstoqueDTO estoqueDTO){
        try {
            Anunciante anunciante = anuncianteService.findUsuario(idAnunciante);
            Produto produto = produtoService.findProduto(estoqueDTO.getIdProduto());
            return estoqueService.updateEstoque(anunciante, produto, estoqueDTO.getQuantidade());
        } catch (UsuarioExCustom | ProdutoExCustom err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
        } catch (EstoqueExCustom err) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, err.getMessage());
        }
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
