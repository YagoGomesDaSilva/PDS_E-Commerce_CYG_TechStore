package com.ufrn.imd.ecommerce.models.entidades;

import com.ufrn.imd.ecommerce.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "VARCHAR(50)")
    protected String nome;
    @Email
    @Column(columnDefinition = "VARCHAR(50)")
    protected String email;
    @Column(columnDefinition = "VARCHAR(100)")
    protected String senha;
    @Column(columnDefinition = "VARCHAR(15)")
    protected String numeroTelefone;
    @Column
    protected boolean tipo;
    protected Double credito;
    protected TipoUsuario tipoUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Endereco> enderecos;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String numeroTelefone, Double credito, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.numeroTelefone = numeroTelefone;
        this.credito = credito;
        this.tipoUsuario = tipoUsuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario that = (Usuario) o;
        return tipo == that.tipo && Objects.equals(nome, that.nome) && Objects.equals(email, that.email) && Objects.equals(senha, that.senha) && Objects.equals(numeroTelefone, that.numeroTelefone) && Objects.equals(credito, that.credito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, senha, numeroTelefone, tipo, credito);
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
}
