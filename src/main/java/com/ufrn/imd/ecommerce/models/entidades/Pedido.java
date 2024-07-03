package com.ufrn.imd.ecommerce.models.entidades;

import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.TipoPagamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double valorTotal;
    @Column(scale = 2)
    private double valorFrete;
    @Column(scale = 2)
    private double valorTotalItens;
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private StatusPedido statusPedido;
    @Column
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido")
    private Set<PedidoItem> pedidoItems;


    public Pedido() {
    }


    public Pedido(double valorTotal, double valorFrete, double valorTotalItens, LocalDate data, StatusPedido statusPedido, TipoPagamento tipoPagamento, Usuario usuario, Set<PedidoItem> pedidoItems) {
        this.valorTotal = valorTotal;
        this.valorFrete = valorFrete;
        this.valorTotalItens = valorTotalItens;
        this.data = data;
        this.statusPedido = statusPedido;
        this.tipoPagamento = tipoPagamento;
        this.usuario = usuario;
        this.pedidoItems = pedidoItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public double getValorTotalItens() {
        return valorTotalItens;
    }

    public void setValorTotalItens(double valorTotalItens) {
        this.valorTotalItens = valorTotalItens;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<PedidoItem> getPedidoItems() {
        return pedidoItems;
    }

    public void setPedidoItems(Set<PedidoItem> pedidoItems) {
        this.pedidoItems = pedidoItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id) && Objects.equals(valorTotal, pedido.valorTotal) && Objects.equals(valorFrete, pedido.valorFrete) && Objects.equals(valorTotalItens, pedido.valorTotalItens) && Objects.equals(data, pedido.data) && Objects.equals(statusPedido, pedido.statusPedido) && tipoPagamento == pedido.tipoPagamento && Objects.equals(usuario, pedido.usuario) && Objects.equals(pedidoItems, pedido.pedidoItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valorTotal, valorFrete, valorTotalItens, data, statusPedido, tipoPagamento, usuario, pedidoItems);
    }
}
