package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.models.entidades.Imagem;
import com.ufrn.imd.ecommerce.services.ImagemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

    private final ImagemService imagemService;

    public ImagemController(ImagemService imagemService) {
        this.imagemService = imagemService;
    }

    @PostMapping
    public void salvarImagem(@RequestBody Imagem imagem) {
        imagemService.saveImage(imagem);
    }

    @GetMapping("/produto/{idProduto}")
    public List<Imagem> encontrarImagensPorProduto(@PathVariable Long idProduto) {
        return imagemService.findImagensByProduto(idProduto);
    }
}
