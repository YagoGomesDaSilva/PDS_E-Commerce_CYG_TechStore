package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.models.Imagem;
import com.ufrn.imd.ecommerce.models.Produto;
import com.ufrn.imd.ecommerce.models.ProdutoImagemDTO;
import com.ufrn.imd.ecommerce.services.ImagemService;
import com.ufrn.imd.ecommerce.services.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            Produto produto = produtoService.findProduto(idProduto);
            produto.setImagems(imagemService.findImagensByProduto(idProduto));
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

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/produto")
    public ResponseEntity<?> updateProduto(@RequestBody Produto produto) {
        try {
            produtoService.updateProduto(produto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
