package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.InvalidFirstNameException;
import com.ufrn.imd.ecommerce.models.UsuarioConcreto;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

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

    public void createUsuario(UsuarioConcreto usuario) throws InvalidFirstNameException {

        // Verificar se há apenas caracteres do alfabeto no nome
        if (!usuario.getNome().matches("[a-zA-Z]+")) {
            throw new InvalidFirstNameException();
        }

        usuario.setNome(usuario.getNome().trim());


        usuarioRepository.save(usuario);
    }
}
