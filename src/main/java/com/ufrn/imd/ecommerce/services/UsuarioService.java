package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.UsuarioConcreto;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioConcreto findUsuario(Long idUsuario) throws Exception {
        UsuarioConcreto usuario = usuarioRepository.findById(idUsuario).isPresent() ? usuarioRepository.findById(idUsuario).get() : null;
        if (usuario == null){
            throw new Exception();
        }
        return usuario;
    }

    public List<UsuarioConcreto> findUsuarios() throws Exception{
        List<UsuarioConcreto> usuarios = usuarioRepository.findAll();
        if(usuarios.isEmpty()){
            throw new Exception();
        }
        return usuarios;
    }

    public void createUsuario(UsuarioConcreto usuario) {
        // to-do validações para criação do usuário;
        usuarioRepository.save(usuario);
    }

    public void updateUsuario(UsuarioConcreto usuario) throws Exception{
        if(usuarioRepository.findById(usuario.getId()).isPresent()){
            //to-do implementar update em Usuario
        } else {
            throw new Exception("Usuario não encontrado");
        }
    }

    public void deletarUsuario(UsuarioConcreto usuario) throws Exception{
        if(usuarioRepository.findById(usuario.getId()).isPresent()){
            usuarioRepository.delete(usuario);
        } else {
            throw new Exception("Usuario não encontrado");
        }
    }
}
