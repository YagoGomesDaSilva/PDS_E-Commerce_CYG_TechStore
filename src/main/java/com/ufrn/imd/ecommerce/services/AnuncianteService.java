package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
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

    public Anunciante findAnunciante(Long idAnunciante) {
        Anunciante anunciante = anuncianteRepository.findById(idAnunciante).isPresent() ? anuncianteRepository.findById(idAnunciante).get() : null;
        if (anunciante == null){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return anunciante;
    }

    public List<Anunciante> findAnunciantes() {
        List<Anunciante> anunciantes = anuncianteRepository.findAll();
        if(anunciantes.isEmpty()){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return anunciantes;
    }

    public void createAnunciante(Anunciante anunciante) {
        // to-do validações para criação do Anunciante;
        anuncianteRepository.save(anunciante);
    }

    public void updateAnunciante(Anunciante anunciante) {
        if(anuncianteRepository.findById(anunciante.getId()).isPresent()){
            //to-do implementar update em Anuncio
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);        }
    }

    public void deletarAnunciante(Anunciante anunciante) {
        if(anuncianteRepository.findById(anunciante.getId()).isPresent()){
            anuncianteRepository.delete(anunciante);
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }



}
