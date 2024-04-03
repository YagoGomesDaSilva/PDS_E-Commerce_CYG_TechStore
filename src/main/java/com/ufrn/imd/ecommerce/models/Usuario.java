package com.ufrn.imd.ecommerce.models;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;

    @Column(columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(columnDefinition = "VARCHAR(50)")
    private String senha;

    @Column(columnDefinition = "VARCHAR(11)")
    private String cpf;

    @Column(columnDefinition = "VARCHAR(15)")
    private String numeroTelefone;

    @Column(columnDefinition = "VARCHAR(20)")
    private String tipo;

    @Column(columnDefinition = "VARCHAR(20)")
    private String situacao;

    public long getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeUsuario) {
        this.nome = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emailUsuario) {
        this.email = emailUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipoUsuario) {
        this.tipo = tipoUsuario;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacaoUsuario) {
        this.situacao = situacaoUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(columnDefinition = "VARCHAR(100)")
    private String token;
}
