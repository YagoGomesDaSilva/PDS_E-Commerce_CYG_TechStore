package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Anuncio anuncio;

    private LocalDateTime horaDaSolicitacao;

    public Notificacao() {}

    public Notificacao(Usuario usuario, Anuncio anuncio) {
        this.usuario = usuario;
        this.anuncio = anuncio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public LocalDateTime getHoraDaSolicitacao() {
        return horaDaSolicitacao;
    }

    public void setHoraDaSolicitacao(LocalDateTime horaDaSolicitacao) {
        this.horaDaSolicitacao = horaDaSolicitacao;
    }
}
