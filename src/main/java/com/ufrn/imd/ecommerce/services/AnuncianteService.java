package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.config.SecurityFilter;
import com.ufrn.imd.ecommerce.config.TokenService;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
import com.ufrn.imd.ecommerce.repositories.AnuncianteRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncianteService extends UsuarioService {

    private final AnuncianteRepository anuncianteRepository;
    private final TokenService tokenService;
    private final SecurityFilter securityFilter;

    public AnuncianteService(AnuncianteRepository anuncianteRepository, TokenService tokenService, SecurityFilter securityFilter) {
        super();
        this.anuncianteRepository = anuncianteRepository;
        this.tokenService = tokenService;
        this.securityFilter = securityFilter;
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

    public void createAnunciante(Anunciante anunciante, UsuarioConcreto usuario) {
        if(anunciante.getNomeAnunciante().equals("")){
            anunciante.setNomeAnunciante(usuario.getNome());
        }
        anunciante.setNome(usuario.getNome());
        anunciante.setEmail(usuario.getEmail());
        anunciante.setNumeroTelefone(usuario.getNumeroTelefone());
        anunciante.setSenha(usuario.getSenha());
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


    public Anunciante findByToken(HttpServletRequest request) {
        var token = tokenService.resolveToken(request);
        var user = tokenService.validateToken(token);
        var anunciante = anuncianteRepository.findByEmail(user);
        if(user.equals("") || anunciante == null){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return anunciante;
    }

    public Anunciante findByEmail(String email) {
        return anuncianteRepository.findByEmail(email);
    }
}
