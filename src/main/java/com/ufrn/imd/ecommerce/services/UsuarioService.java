package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioConcreto findUsuario(Long idUsuario) throws UsuarioExCustom {
        UsuarioConcreto usuario = usuarioRepository.findById(idUsuario).isPresent() ? usuarioRepository.findById(idUsuario).get() : null;
        if (usuario == null){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return usuario;
    }

    public List<UsuarioConcreto> findUsuarios() throws UsuarioExCustom {
        List<UsuarioConcreto> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new UsuarioExCustom(UsuarioEnumEx.SEM_USUARIOS_CADASTRADOS);
        }
        return usuarios;
    }

    public void createUsuarioConcreto(UsuarioConcreto usuario) throws UsuarioExCustom {
        // Verificar se há apenas caracteres do alfabeto no nome
        if (!usuario.getNome().matches("[a-zA-Z]+")) {
            throw new UsuarioExCustom(UsuarioEnumEx.NOME_USUARIO_INVALIDO);
        }
        usuario.setNome(usuario.getNome().trim());
        usuarioRepository.save(usuario);
    }

    public void createUsuario(UsuarioConcreto usuario) throws UsuarioExCustom {
        if(usuarioRepository.findUsuarioConcretoByEmail(usuario.getEmail()) != null) {
            throw new UsuarioExCustom(UsuarioEnumEx.EMAIL_DUPLICADO);
        }
        usuarioRepository.save(usuario);
    }

    public void updateUsuario(UsuarioConcreto usuario) throws UsuarioExCustom {
        if(usuarioRepository.findById(usuario.getId()).isPresent()){
            //to-do implementar update em Usuario
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
}
