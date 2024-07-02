package com.ufrn.imd.ecommerce.models.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "VARCHAR(14)")
    private String numDocumento;
    @Column
    private Integer quantidade;
    @Column(columnDefinition = "VARCHAR(20)")
    private String localEstoque;

    @ManyToOne
    @JoinColumn(name = "anunciante_id")
    @JsonIgnore
    private Anunciante anunciante;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    @JsonIgnore
    private Produto produto;

    public Estoque() {

    }

    public Estoque(String numDocumento, Integer quantidade, String localEstoque, Anunciante anunciante, Produto produto) {
        this.numDocumento = numDocumento;
        this.quantidade = quantidade;
        this.localEstoque = localEstoque;
        this.anunciante = anunciante;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getLocalEstoque() {
        return localEstoque;
    }

    public void setLocalEstoque(String localEstoque) {
        this.localEstoque = localEstoque;
    }

    public Anunciante getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(Anunciante anunciante) {
        this.anunciante = anunciante;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estoque estoque = (Estoque) o;
        return Objects.equals(id, estoque.id) && Objects.equals(numDocumento, estoque.numDocumento) && Objects.equals(quantidade, estoque.quantidade) && Objects.equals(localEstoque, estoque.localEstoque) && Objects.equals(anunciante, estoque.anunciante) && Objects.equals(produto, estoque.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numDocumento, quantidade, localEstoque, anunciante, produto);
    }
}
