package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

@Entity
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    public Anuncio() {

    }
}
