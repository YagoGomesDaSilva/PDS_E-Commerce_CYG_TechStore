package com.ufrn.imd.ecommerce.services.interfaces;


import com.ufrn.imd.ecommerce.models.entidades.UsuarioAbstrato;
import jakarta.servlet.http.HttpServletRequest;

public interface UsuarioService {
    public UsuarioAbstrato findUsuario(Long idUsuario);

    public UsuarioAbstrato createUsuario(UsuarioAbstrato usuario);

    public UsuarioAbstrato updateUsuario(UsuarioAbstrato usuario);

    public void deletarUsuario(UsuarioAbstrato usuario);

    public UsuarioAbstrato findUsuarioByToken(HttpServletRequest request);

    public UsuarioAbstrato findByEmail(String email);
}
