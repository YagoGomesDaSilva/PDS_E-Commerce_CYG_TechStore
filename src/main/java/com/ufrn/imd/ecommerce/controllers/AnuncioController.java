package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.error.exceptions.AnuncioExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.DTO.AnuncioDTO;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Anuncio;
import com.ufrn.imd.ecommerce.models.entidades.Imagem;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.services.AnuncioService;
import com.ufrn.imd.ecommerce.services.EstoqueService;
import com.ufrn.imd.ecommerce.services.ImagemService;
import com.ufrn.imd.ecommerce.services.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/anuncio")
public class AnuncioController {

    private final ProdutoService produtoService;
    private final ImagemService imagemService;
    private final AnuncioService anuncioService;

    private final EstoqueService estoqueService;

    public AnuncioController(ProdutoService produtoService, ImagemService imagemService, AnuncioService anuncioService,EstoqueService estoqueService) {
        this.produtoService = produtoService;
        this.imagemService = imagemService;
        this.anuncioService = anuncioService;
        this.estoqueService = estoqueService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Anuncio create(@RequestBody AnuncioDTO anuncioDTO, HttpServletRequest request) {
        try {
            Anunciante anunciante = anuncioService.createAnuncio(anuncioDTO.getAnuncio(), request);
            Produto produto = produtoService.createProduto(anuncioDTO.getProduto());

            estoqueService.createEstoque(anunciante.getId(),produto);

            List<Imagem> imagens = anuncioDTO.getImagem();
            for (Imagem imagem : imagens ) {
                imagem.setProduto(anuncioDTO.getProduto());
                imagemService.saveImage(imagem);
            }

            Optional<Anuncio> anuncio = anuncioService.findAnuncio(anuncioDTO.getAnuncio().getId());

            if (anuncio.isPresent()){
                return anuncio.get();
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        } catch (UsuarioExCustom e) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idAnuncio}")
    public AnuncioDTO getAnuncio(@PathVariable Long idAnuncio) {
        try {
            AnuncioDTO anuncioDTO = new AnuncioDTO();
            anuncioDTO.setAnuncio( anuncioService.findAnuncio(idAnuncio).get());

            anuncioDTO.setProduto(produtoService.findProdutoByAnuncio(idAnuncio));
            anuncioDTO.setImagem(imagemService.findImagensByProduto(anuncioDTO.getProduto().getId()));

            Long anuncianteId = anuncioDTO.getAnuncio().getAnunciante().getId();
            anuncioDTO.setEstoque(estoqueService.findEstoque(anuncianteId, anuncioDTO.getProduto()).get());

            return anuncioDTO;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    public List<Anuncio> getAllAnuncios() {
        try {
            List<Anuncio> anuncios = anuncioService.findAnuncios();
            anuncios.forEach(a -> {
                a.setProduto(produtoService.findProdutoByAnuncio(a.getId()));
                if (a.getProduto() != null) {
                    a.getProduto().setImagems(imagemService.findImagensByProduto(a.getProduto().getId()));
                }
            });
            return anuncios;
        } catch (AnuncioExCustom e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
