package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
//@Table(name = "Produtos")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Produto() {
    }

    public Long getIdProduto() {
        return this.idProduto;
    }
}
