package com.ufrn.imd.ecommerce.models;

import jakarta.persistence.*;

@Entity
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAnuncio;

    @Column(columnDefinition = "VARCHAR(100)")
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @OneToOne
    private Produto produto;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String tituloAnuncio) {
        this.titulo = tituloAnuncio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricaoAnuncio) {
        this.descricao = descricaoAnuncio;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
