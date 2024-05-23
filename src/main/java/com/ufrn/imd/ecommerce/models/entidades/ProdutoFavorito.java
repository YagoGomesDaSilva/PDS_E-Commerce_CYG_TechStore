package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

@Entity
public class ProdutoFavorito {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsuarioConcreto usuarioConcreto;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public ProdutoFavorito() {}

    public ProdutoFavorito(UsuarioConcreto usuarioConcreto, Produto produto) {
        this.usuarioConcreto = usuarioConcreto;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioConcreto getUsuarioConcreto() {
        return usuarioConcreto;
    }

    public void setUsuarioConcreto(UsuarioConcreto usuarioConcreto) {
        this.usuarioConcreto = usuarioConcreto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


}
