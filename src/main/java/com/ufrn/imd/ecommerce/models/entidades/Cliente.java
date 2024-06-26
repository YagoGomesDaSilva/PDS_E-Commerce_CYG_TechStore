package com.ufrn.imd.ecommerce.models.entidades;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufrn.imd.ecommerce.enums.TipoUsuario;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("usuario")
public  class Cliente extends UsuarioAbstrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cartao> cartoes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovimentacaoEstoque> movimentacoesEstoque;

    public Cliente() {
        super();
    }

    public Cliente(String nome, String email, String senha, String numeroTelefone, Double credito, List<Cartao> cartoes, List<Pedido> pedidos, List<MovimentacaoEstoque> movimentacoesEstoque, TipoUsuario tipoUsuario) {
        super(nome, email, senha, numeroTelefone, credito, tipoUsuario);
        this.cartoes = cartoes;
        this.pedidos = pedidos;
        this.movimentacoesEstoque = movimentacoesEstoque;
    }

    public Cliente(String email, String encyptedPassword) {
        this.email = email;
        this.senha = encyptedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<MovimentacaoEstoque> getMovimentacoesEstoque() {
        return movimentacoesEstoque;
    }

    public void setMovimentacoesEstoque(List<MovimentacaoEstoque> movimentacoesEstoque) {
        this.movimentacoesEstoque = movimentacoesEstoque;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente that = (Cliente) o;
        return Objects.equals(cartoes, that.cartoes) && Objects.equals(pedidos, that.pedidos) && Objects.equals(movimentacoesEstoque, that.movimentacoesEstoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cartoes, pedidos, movimentacoesEstoque);
    }
}
