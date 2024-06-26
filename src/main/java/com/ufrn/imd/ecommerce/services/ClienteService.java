package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.config.TokenService;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Cliente;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioAbstrato;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;
import com.ufrn.imd.ecommerce.services.interfaces.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    public ClienteService() {
        //to-do
    }

    public Cliente findUsuario(Long idUsuario) throws UsuarioExCustom {
        Cliente usuario = usuarioRepository.findById(idUsuario).isPresent() ? usuarioRepository.findById(idUsuario).get() : null;
        if (usuario == null){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return usuario;
    }

    @Override
    public Cliente createUsuario(UsuarioAbstrato usuario) {
        Cliente cliente = (Cliente) usuario;
        if (!usuario.getNome().matches("[a-zA-Z]+")) {
            throw new UsuarioExCustom(UsuarioEnumEx.NOME_USUARIO_INVALIDO);
        }
        if(usuarioRepository.findUsuarioConcretoByEmail(usuario.getEmail()) != null) {
            throw new UsuarioExCustom(UsuarioEnumEx.EMAIL_DUPLICADO);
        }
        return usuarioRepository.save(cliente);
    }

    @Override
    public Cliente updateUsuario(UsuarioAbstrato usuario) {
        Cliente cliente = (Cliente) usuario;
        if(usuarioRepository.findById(cliente.getId()).isPresent()){
            return usuarioRepository.save(cliente);
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    @Override
    public void deletarUsuario(UsuarioAbstrato usuario) {
        Cliente cliente = (Cliente) usuario;
        if(usuarioRepository.findById(cliente.getId()).isPresent()){
            usuarioRepository.delete(cliente);
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    public Cliente findUsuarioByToken(HttpServletRequest request) {
        var token = tokenService.resolveToken(request);
        var user = tokenService.validateToken(token);
        if(user.equals("")){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return usuarioRepository.findUsuarioConcretoByEmail(user);
    }

    @Override
    public UsuarioAbstrato findByEmail(String email) {
        return null;
    }
}
