package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.error.exceptions.AnuncioExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.EstoqueExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.ProdutoExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.DTO.AnuncioDTO;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Anuncio;
import com.ufrn.imd.ecommerce.models.entidades.Imagem;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/anuncio")
public class AnuncioController {

    private final ProdutoService produtoService;
    private final ImagemService imagemService;
    private final AnuncioService anuncioService;
    private final EstoqueService estoqueService;
    private final AnuncianteService anuncianteService;

    public AnuncioController(ProdutoService produtoService, ImagemService imagemService, AnuncioService anuncioService, EstoqueService estoqueService, AnuncianteService anuncianteService) {
        this.produtoService = produtoService;
        this.imagemService = imagemService;
        this.anuncioService = anuncioService;
        this.estoqueService = estoqueService;
        this.anuncianteService = anuncianteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Anuncio create(@RequestBody AnuncioDTO anuncioDTO, HttpServletRequest request) {
        try {
            Anunciante anunciante = anuncianteService.findUsuarioByToken(request);
            Produto produto = produtoService.createProduto(anuncioDTO.getProduto(), anunciante);
            Anuncio anuncio = anuncioService.createAnuncio(anuncioDTO.getAnuncio(), anunciante);

            produtoService.addAnuncio(produto, anuncio);

            estoqueService.createEstoque(anuncioDTO.getEstoque(), anunciante.getId(), produto);

            List<Imagem> imagens = new ArrayList<>();
            for (Imagem imagem : anuncioDTO.getImagem() ) {
                imagem.setProduto(anuncioDTO.getProduto());
                imagens.add(imagemService.saveImage(imagem));
            }

            produtoService.addImagens(produto, imagens);
            anuncioService.addProduto(produto, anuncio);

            return anuncio;
        } catch (UsuarioExCustom e) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ProdutoExCustom | AnuncioExCustom | EstoqueExCustom e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{idAnuncio}")
    public Anuncio getAnuncio(@PathVariable Long idAnuncio) {
        try {
            return anuncioService.findAnuncio(idAnuncio).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    public List<Anuncio> getAllAnuncios() {
        try {
            List<Anuncio> anuncios = anuncioService.findAnuncios();
            return anuncios;
        } catch (AnuncioExCustom e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
