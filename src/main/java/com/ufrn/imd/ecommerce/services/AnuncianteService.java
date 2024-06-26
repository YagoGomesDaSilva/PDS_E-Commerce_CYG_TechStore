package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.config.SecurityFilter;
import com.ufrn.imd.ecommerce.config.TokenService;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioAbstrato;
import com.ufrn.imd.ecommerce.repositories.AnuncianteRepository;
import com.ufrn.imd.ecommerce.services.interfaces.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncianteService implements UsuarioService {

    @Autowired
    private AnuncianteRepository anuncianteRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SecurityFilter securityFilter;

    public List<Anunciante> findAnunciantes() {
        List<Anunciante> anunciantes = anuncianteRepository.findAll();
        if(anunciantes.isEmpty()){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return anunciantes;
    }

    public Anunciante findByEmail(String email) {
        return anuncianteRepository.findByEmail(email);
    }

    @Override
    public Anunciante findUsuario(Long idUsuario) {
        Anunciante anunciante = anuncianteRepository.findById(idUsuario).isPresent() ? anuncianteRepository.findById(idUsuario).get() : null;
        if (anunciante == null){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return anunciante;
    }

    @Override
    public Anunciante createUsuario(UsuarioAbstrato usuario) {
        Anunciante anunciante = (Anunciante) usuario;
        if(anunciante.getNomeAnunciante().equals("")){
            anunciante.setNomeAnunciante(usuario.getNome());
        }
        anunciante.setNome(usuario.getNome());
        anunciante.setEmail(usuario.getEmail());
        anunciante.setNumeroTelefone(usuario.getNumeroTelefone());
        anunciante.setSenha(usuario.getSenha());
        return anuncianteRepository.save(anunciante);
    }

    @Override
    public Anunciante updateUsuario(UsuarioAbstrato usuario) {
        Anunciante anunciante = (Anunciante) usuario;
        if(anuncianteRepository.findById(anunciante.getId()).isPresent()){
            return anuncianteRepository.save(anunciante);
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    @Override
    public void deletarUsuario(UsuarioAbstrato usuario) {
        Anunciante anunciante = (Anunciante) usuario;
        if(anuncianteRepository.findById(anunciante.getId()).isPresent()){
            anuncianteRepository.delete(anunciante);
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    @Override
    public Anunciante findUsuarioByToken(HttpServletRequest request) {
        var token = tokenService.resolveToken(request);
        var user = tokenService.validateToken(token);
        var anunciante = anuncianteRepository.findByEmail(user);
        if(user.equals("") || anunciante == null){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return anunciante;
    }
}
