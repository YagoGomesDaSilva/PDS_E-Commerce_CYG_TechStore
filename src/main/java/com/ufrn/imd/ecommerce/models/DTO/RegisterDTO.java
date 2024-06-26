package com.ufrn.imd.ecommerce.models.DTO;

import com.ufrn.imd.ecommerce.enums.TipoUsuario;
import com.ufrn.imd.ecommerce.models.entidades.Endereco;

public class RegisterDTO {

    private String email;
    private String password;
    private String nome;
    private String telefone;
    private String documento;
    private Endereco endereco;
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

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getDocumento() {
        return documento;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
