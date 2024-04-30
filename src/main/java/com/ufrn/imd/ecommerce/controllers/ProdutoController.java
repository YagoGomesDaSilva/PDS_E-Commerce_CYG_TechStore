package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.error.enunsEx.ProdutoEnumEx;
import com.ufrn.imd.ecommerce.models.entidades.Imagem;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.models.DTO.ProdutoImagemDTO;
import com.ufrn.imd.ecommerce.services.ImagemService;
import com.ufrn.imd.ecommerce.services.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ImagemService imagemService;

    public ProdutoController(ProdutoService produtoService, ImagemService imagemService) {
        this.produtoService = produtoService;
        this.imagemService = imagemService;
    }

    @GetMapping(value = "/produto")
    public ResponseEntity<?> getProduto(@RequestParam(value = "idProduto") Long idProduto){
        try{
            Optional<Produto> produto = produtoService.findProduto(idProduto);
            produto.ifPresent(value -> value.setImagems(imagemService.findImagensByProduto(idProduto)));
            return new ResponseEntity<>(produto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/produtos")
    public ResponseEntity<?> getProdutos(){
        try {
            List<Produto> produtos = produtoService.findProdutos();
            for(Produto produto : produtos){
                produto.setImagems(imagemService.findImagensByProduto(produto.getId()));
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/produto")
    public ResponseEntity<?> createProduto(@RequestBody ProdutoImagemDTO produtoImagemDTO){
        try {
            produtoService.createProduto(produtoImagemDTO.getProduto());

            Imagem imagem = produtoImagemDTO.getImagem();
            imagem.setProduto(produtoImagemDTO.getProduto());
            imagemService.saveImage(imagem);

            return new ResponseEntity<>(CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/produto")
    public ResponseEntity<?> updateProduto(@RequestBody Produto produto) {
        try {
            produtoService.updateProduto(produto);
            return new ResponseEntity<>(CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save( @RequestBody Produto produto ){
        return produtoService.createProduto(produto);
    }


    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update( @PathVariable Long id, @RequestBody Produto produto )  {
        produtoService
                .findProduto(id)
                .map( p -> {
                    produto.setId(p.getId());
                        produtoService.updateProduto(produto);
                    return produto;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                ProdutoEnumEx.PRODUTO_NAO_ENCONTRADO.getMensagem()));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        produtoService
                .findProduto(id)
                .map( p -> {
                        produtoService.deletarProduto(p);
                    return Void.TYPE;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                ProdutoEnumEx.PRODUTO_NAO_ENCONTRADO.getMensagem()));
    }

    @GetMapping("{id}")
    public Produto getById(@PathVariable Long id) {
        return produtoService
                .findProduto(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                ProdutoEnumEx.PRODUTO_NAO_ENCONTRADO.getMensagem()));
    }

}
