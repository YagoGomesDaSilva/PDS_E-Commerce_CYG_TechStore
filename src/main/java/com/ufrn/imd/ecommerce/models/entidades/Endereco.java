package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    private String cidade;
    @Column(columnDefinition = "VARCHAR(100)")
    private String logradouro;
    @Column(columnDefinition = "VARCHAR(8)")
    private Integer numero;
    @Column(columnDefinition = "VARCHAR(50)")
    private String complemento;
    @Column(columnDefinition = "VARCHAR(50)")
    private String referencia;
    @Column(columnDefinition = "VARCHAR(50)")
    private String bairro;
    @Column(columnDefinition = "VARCHAR(8)")
    private String cep;

    public Endereco() {

    }
}
