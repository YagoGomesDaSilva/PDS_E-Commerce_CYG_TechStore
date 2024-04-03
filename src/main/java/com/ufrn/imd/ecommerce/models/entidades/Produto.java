package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
//@Table(name = "Produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    @Column(columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(scale = 2)
    private BigDecimal valorTotal;

    @Column(columnDefinition = "VARCHAR(50)")
    private String marca;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    public Produto() {
    }

    public Long getIdProduto() {
        return this.idProduto;
    }
}
