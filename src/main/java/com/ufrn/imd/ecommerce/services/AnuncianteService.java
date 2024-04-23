package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.repositories.AnuncianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncianteService {

    private final AnuncianteRepository anuncianteRepository;

    public AnuncianteService(AnuncianteRepository anuncianteRepository) {
        this.anuncianteRepository = anuncianteRepository;
    }

    public Anunciante findAnunciante(Long idAnunciante) throws Exception {
        Anunciante anunciante = anuncianteRepository.findById(idAnunciante).isPresent() ? anuncianteRepository.findById(idAnunciante).get() : null;
        if (anunciante == null){
            throw new Exception();
        }
        return anunciante;
    }

    public List<Anunciante> findAnunciantes() throws Exception{
        List<Anunciante> anunciantes = anuncianteRepository.findAll();
        if(anunciantes.isEmpty()){
            throw new Exception();
        }
        return anunciantes;
    }

    public void createAnunciante(Anunciante anunciante) {
        // to-do validações para criação do Anunciante;
        anuncianteRepository.save(anunciante);
    }

    public void updateAnunciante(Anunciante anunciante) throws Exception{
        if(anuncianteRepository.findById(anunciante.getId()).isPresent()){
            //to-do implementar update em Anuncio
        } else {
            throw new Exception("Anunciante não encontrado");
        }
    }

    public void deletarAnunciante(Anunciante anunciante) throws Exception{
        if(anuncianteRepository.findById(anunciante.getId()).isPresent()){
            anuncianteRepository.delete(anunciante);
        } else {
            throw new Exception("Anunciante não encontrado");
        }
    }



}
