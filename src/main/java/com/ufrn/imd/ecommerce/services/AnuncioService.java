package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.AnuncioEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.AnuncioExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Anuncio;
import com.ufrn.imd.ecommerce.repositories.AnuncioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnuncioService {

    private final AnuncioRepository anuncioRepository;
    private final AnuncianteService anuncianteService;

    public AnuncioService(AnuncioRepository anuncioRepository, AnuncianteService anuncianteService) {
        this.anuncioRepository = anuncioRepository;
        this.anuncianteService = anuncianteService;
    }

    public Optional<Anuncio> findAnuncio(Long idAnuncio) throws Exception {
        Anuncio anuncio = anuncioRepository.findById(idAnuncio).isPresent() ? anuncioRepository.findById(idAnuncio).get() : null;
        if (anuncio == null){
            throw new AnuncioExCustom(AnuncioEnumEx.ANUNCIO_NAO_ENCONTRADO);
        }
        return Optional.of(anuncio);
    }

    public List<Anuncio> findAnuncios() throws AnuncioExCustom {
        List<Anuncio> anuncios = anuncioRepository.findAll();
        if(anuncios.isEmpty()){
            throw new AnuncioExCustom(AnuncioEnumEx.NENHUM_ANUNCIO_ENCONTRADO);
        }
        return anuncios;
    }

    public void createAnuncio(Anuncio anuncio, HttpServletRequest request) {
        Anunciante anunciante = anuncianteService.findByEmail(request);
        if(anunciante != null) {
            anuncio.setAnunciante(anunciante);
            anuncioRepository.save(anuncio);
            return;
        }

        throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
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
