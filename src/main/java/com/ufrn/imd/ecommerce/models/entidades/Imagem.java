package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idImagem;

    @Column(columnDefinition = "VARCHAR(100)")
    private String caminhoImagem;

    public Imagem() {

    }
}
