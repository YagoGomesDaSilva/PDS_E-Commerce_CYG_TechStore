package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.config.TokenService;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    public UsuarioService() {
        //to-do
    }

    public UsuarioConcreto findUsuario(Long idUsuario) throws UsuarioExCustom {
        UsuarioConcreto usuario = usuarioRepository.findById(idUsuario).isPresent() ? usuarioRepository.findById(idUsuario).get() : null;
        if (usuario == null){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return usuario;
    }

    public UsuarioConcreto createUsuario(UsuarioConcreto usuario) throws UsuarioExCustom {
        if (!usuario.getNome().matches("[a-zA-Z]+")) {
            throw new UsuarioExCustom(UsuarioEnumEx.NOME_USUARIO_INVALIDO);
        }
        if(usuarioRepository.findUsuarioConcretoByEmail(usuario.getEmail()) != null) {
            throw new UsuarioExCustom(UsuarioEnumEx.EMAIL_DUPLICADO);
        }
        return usuarioRepository.save(usuario);
    }

    public UsuarioConcreto updateUsuario(UsuarioConcreto usuario) throws UsuarioExCustom {
        if(usuarioRepository.findById(usuario.getId()).isPresent()){
            return usuarioRepository.save(usuario);
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    public void deletarUsuario(UsuarioConcreto usuario) throws UsuarioExCustom {
        if(usuarioRepository.findById(usuario.getId()).isPresent()){
            usuarioRepository.delete(usuario);
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    public UsuarioConcreto findUsuarioByToken(HttpServletRequest request) {
        var token = tokenService.resolveToken(request);
        var user = tokenService.validateToken(token);
        if(user.equals("")){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return usuarioRepository.findUsuarioConcretoByEmail(user);
    }
}
