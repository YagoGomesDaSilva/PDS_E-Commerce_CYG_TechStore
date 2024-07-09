package com.ufrn.imd.ecommerce.models.DTO;

public class AuthDTO {
    private String token;
    private Long idUsuario;
    private String nome;
    private String email;
    private String tipoUsuario;

    public AuthDTO(String token, String tipoUsuario) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
    }

    public AuthDTO(String token, Long idUsuario, String nome, String email, String tipoUsuario) {
        this.token = token;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
