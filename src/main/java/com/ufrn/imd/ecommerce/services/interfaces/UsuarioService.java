package com.ufrn.imd.ecommerce.services.interfaces;


import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import jakarta.servlet.http.HttpServletRequest;

public interface UsuarioService {
    public Usuario findUsuario(Long idUsuario);

    public Usuario createUsuario(Usuario usuario);

    public Usuario updateUsuario(Usuario usuario);

    public void deletarUsuario(Usuario usuario);

    public Usuario findUsuarioByToken(HttpServletRequest request);

    public Usuario findByEmail(String email);
}
