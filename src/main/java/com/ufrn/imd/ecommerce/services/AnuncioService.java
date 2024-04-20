package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.Anuncio;
import com.ufrn.imd.ecommerce.models.UsuarioConcreto;
import com.ufrn.imd.ecommerce.repositories.AnuncioRepository;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncioService {

    private final AnuncioRepository anuncioRepository;

    public AnuncioService(AnuncioRepository anuncioRepository) {
        this.anuncioRepository = anuncioRepository;
    }

    public Anuncio findAnuncio(Long idAnuncio) throws Exception {
        Anuncio anuncio = anuncioRepository.findById(idAnuncio).isPresent() ? anuncioRepository.findById(idAnuncio).get() : null;
        if (anuncio == null){
            throw new Exception();
        }
        return anuncio;
    }

    public List<Anuncio> findAnuncios() throws Exception{
        List<Anuncio> anuncios = anuncioRepository.findAll();
        if(anuncios.isEmpty()){
            throw new Exception();
        }
        return anuncios;
    }

    public void createAnuncio(Anuncio anuncio) {
        // to-do validações para criação do Anuncio;
        anuncioRepository.save(anuncio);
    }

    public void updateAnuncio(Anuncio anuncio) throws Exception{
        if(anuncioRepository.findById(anuncio.getId()).isPresent()){
            //to-do implementar update em Anuncio
        } else {
            throw new Exception("Anuncio não encontrado");
        }
    }

    public void deletarAnuncio(Anuncio anuncio) throws Exception{
        if(anuncioRepository.findById(anuncio.getId()).isPresent()){
            anuncioRepository.delete(anuncio);
        } else {
            throw new Exception("Anuncio não encontrado");
        }
    }

}
