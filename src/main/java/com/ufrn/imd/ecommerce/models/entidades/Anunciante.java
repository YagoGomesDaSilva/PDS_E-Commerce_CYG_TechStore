package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_perfil", discriminatorType = DiscriminatorType.STRING)
public class Anunciante extends UsuarioAbstrato{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(columnDefinition = "VARCHAR(14)")
    String documento;

    @OneToMany(mappedBy = "anunciante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Anuncio> anuncios;
    @OneToMany(mappedBy = "anunciante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<Produto> produtos;
    @OneToOne(mappedBy = "anunciante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Estoque estoque;

    public Anunciante() {
        super();
    }

    public Anunciante(String documento) {
        super();
        this.documento = documento;
    }

    public Anunciante(String nome, String email, String senha, String numeroTelefone, boolean tipo, String token, BigDecimal credito, String documento, List<Anuncio> anuncios, List<Produto> produtos, Estoque estoque) {
        super(nome, email, senha, numeroTelefone, tipo, token, credito);
        this.documento = documento;
        this.anuncios = anuncios;
        this.produtos = produtos;
        this.estoque = estoque;
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

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Anunciante that = (Anunciante) o;
        return Objects.equals(documento, that.documento) && Objects.equals(anuncios, that.anuncios) && Objects.equals(produtos, that.produtos) && Objects.equals(estoque, that.estoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), documento, anuncios, produtos, estoque);
    }
}
