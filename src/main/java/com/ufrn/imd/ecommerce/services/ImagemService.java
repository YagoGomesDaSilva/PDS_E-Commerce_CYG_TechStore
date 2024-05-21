package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Imagem;
import com.ufrn.imd.ecommerce.repositories.ImagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagemService {

    private final ImagemRepository imagemRepository;

    public ImagemService(ImagemRepository imagemRepository) {
        this.imagemRepository = imagemRepository;
    }


    public Imagem saveImage(Imagem imagem) {
        return imagemRepository.save(imagem);
    }

    public List<Imagem> findImagensByProduto(Long idProduto) {
        return imagemRepository.findByProdutoId(idProduto);
    }
}
