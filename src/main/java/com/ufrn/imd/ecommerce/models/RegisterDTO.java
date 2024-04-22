package com.ufrn.imd.ecommerce.models;

import com.ufrn.imd.ecommerce.enums.TipoUsuario;

public class RegisterDTO {

    private String email;
    private String password;
    private TipoUsuario tipoUsuario;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
}
