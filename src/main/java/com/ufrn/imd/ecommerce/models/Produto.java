package com.ufrn.imd.ecommerce.models;

import jakarta.persistence.*;

@Entity
//@Table(name = "Produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduto;

    @Column(columnDefinition = "VARCHAR(50)")
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(columnDefinition = "DOUBLE")
    private double valor;

    @Column(columnDefinition = "VARCHAR(50)")
    private String marca;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    public long getIdProduto() {
        return idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeProduto) {
        this.nome = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricaoProduto) {
        this.descricao = descricaoProduto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valorProduto) {
        this.valor = valorProduto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marcaProduto) {
        this.marca = marcaProduto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacaoProduto) {
        this.observacao = observacaoProduto;
    }
}
