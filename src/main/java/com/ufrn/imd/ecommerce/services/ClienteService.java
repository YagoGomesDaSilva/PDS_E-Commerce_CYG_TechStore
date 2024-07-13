package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.config.TokenService;
import com.ufrn.imd.ecommerce.error.enunsEx.CreditoEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.CreditoExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements com.ufrn.imd.ecommerce.services.interfaces.UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    public ClienteService() {
        //to-do
    }

    public Usuario findUsuario(Long idUsuario) throws UsuarioExCustom {
        Usuario usuario = usuarioRepository.findById(idUsuario).isPresent() ? usuarioRepository.findById(idUsuario).get() : null;
        if (usuario == null){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return usuario;
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        if (!usuario.getNome().matches("[a-zA-Z]+")) {
            throw new UsuarioExCustom(UsuarioEnumEx.NOME_USUARIO_INVALIDO);
        }
        if(usuarioRepository.findUsuarioByEmail(usuario.getEmail()) != null) {
            throw new UsuarioExCustom(UsuarioEnumEx.EMAIL_DUPLICADO);
        }

        usuario.getEnderecos().get(0).setUsuario(usuario);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        if(usuarioRepository.findById(usuario.getId()).isPresent()){
            return usuarioRepository.save(usuario);
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    @Override
    public void deletarUsuario(Usuario usuario) {
        if(usuarioRepository.findById(usuario.getId()).isPresent()){
            usuarioRepository.delete(usuario);
        } else {
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
    }

    public Usuario findUsuarioByToken(HttpServletRequest request) {
        var token = tokenService.resolveToken(request);
        var user = tokenService.validateToken(token);
        if(user.equals("")){
            throw new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO);
        }
        return usuarioRepository.findUsuarioByEmail(user);
    }

    @Override
    public Usuario findByEmail(String email) {
        return null;
    }

    public void addCredito(Usuario usuario, Double credito) {
        if(credito <= 0){
            throw new CreditoExCustom(CreditoEnumEx.VALOR_INVALIDO);
        }
        usuario.setCredito(usuario.getCredito() + credito);
        usuarioRepository.save(usuario);
    }
}
