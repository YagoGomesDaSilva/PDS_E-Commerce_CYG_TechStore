package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.InfoDuplicatedException;
import com.ufrn.imd.ecommerce.error.InvalidFirstNameException;
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

    public List<UsuarioConcreto> findUsuarios() throws Exception {
        List<UsuarioConcreto> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new Exception();
        }
        return usuarios;
    }

    public void createUsuarioConcreto(UsuarioConcreto usuario) throws InvalidFirstNameException {
        // Verificar se há apenas caracteres do alfabeto no nome
        if (!usuario.getNome().matches("[a-zA-Z]+")) {
            throw new InvalidFirstNameException();
        }
        usuario.setNome(usuario.getNome().trim());
        usuarioRepository.save(usuario);
    }

    public void createUsuario(UsuarioConcreto usuario) throws InfoDuplicatedException {
        if(usuarioRepository.findUsuarioConcretoByEmail(usuario.getEmail()) != null) {
            throw new InfoDuplicatedException("Usuário já existe");
        }
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
