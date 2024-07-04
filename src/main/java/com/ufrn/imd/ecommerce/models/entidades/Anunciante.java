package com.ufrn.imd.ecommerce.models.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufrn.imd.ecommerce.enums.TipoUsuario;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Anunciante extends Usuario {
    String nomeAnunciante;

    @Column(columnDefinition = "VARCHAR(14)")
    String documento;

    @OneToMany(mappedBy = "anunciante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Anuncio> anuncios;

    @OneToMany(mappedBy = "anunciante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private  List<Produto> produtos;

    @OneToMany(mappedBy = "anunciante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Estoque> estoques;

    public Anunciante() {
        super();
    }

    public Anunciante(String documento) {
        super();
        this.documento = documento;
    }

    public Anunciante(String nome, String email, String senha, String numeroTelefone, Double credito, String nomeAnunciante, String documento, TipoUsuario tipoUsuario) {
        super(nome, email, senha, numeroTelefone, credito, tipoUsuario);
        this.nomeAnunciante = nomeAnunciante;
        this.documento = documento;
    }

    public Anunciante(String nome, String email, String senha, String numeroTelefone, Double credito, String nomeAnunciante, String documento, List<Endereco> enderecos, TipoUsuario tipoUsuario) {
        super(nome, email, senha, numeroTelefone, credito, tipoUsuario);
        this.documento = documento;
        this.enderecos = enderecos;
        this.nomeAnunciante = "";
    }

    public String getNomeAnunciante() {
        return nomeAnunciante;
    }

    public void setNomeAnunciante(String nomeAnunciante) {
        this.nomeAnunciante = nomeAnunciante;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public List<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(List<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Estoque> getEstoques() {
        return estoques;
    }

    public void setEstoques(List<Estoque> estoques) {
        this.estoques = estoques;
    }

    @Override
    public boolean equals(Object o) {
        Anunciante that = (Anunciante) o;
        return this.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
