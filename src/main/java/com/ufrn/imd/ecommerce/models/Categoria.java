package com.ufrn.imd.ecommerce.models;

import jakarta.persistence.*;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCategoria;

    @Column(columnDefinition = "VARCHAR(50)")
    private String nome;

    public long getIdCategoria() {
        return idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeCategoria) {
        this.nome = nomeCategoria;
    }
}
