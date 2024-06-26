package com.ufrn.imd.ecommerce.models.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufrn.imd.ecommerce.enums.TipoUsuario;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_perfil", discriminatorType = DiscriminatorType.STRING)
public class Anunciante extends UsuarioAbstrato implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    String nomeAnunciante;

    @Column(columnDefinition = "VARCHAR(14)")
    String documento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Endereco> enderecos;

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

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Anunciante that = (Anunciante) o;
        return Objects.equals(documento, that.documento) && Objects.equals(anuncios, that.anuncios) && Objects.equals(produtos, that.produtos) && Objects.equals(estoques, that.estoques);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.tipoUsuario == TipoUsuario.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), documento, anuncios, produtos, estoques);
//    }
}
