package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.Imagem;
import com.ufrn.imd.ecommerce.repositories.ImagemRepository;
import org.springframework.stereotype.Service;

@Service
public class ImagemService {

    private final ImagemRepository imagemRepository;

    public ImagemService(ImagemRepository imagemRepository) {
        this.imagemRepository = imagemRepository;
    }


    public void saveImage(Imagem imagem) {
        imagemRepository.save(imagem);
    }
}
