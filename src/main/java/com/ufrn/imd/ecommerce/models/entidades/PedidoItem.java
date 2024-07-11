package com.ufrn.imd.ecommerce.models.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    protected Produto produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    protected Pedido pedido;

    @Column
    protected Integer quantidade;

    @Enumerated(EnumType.STRING)
    protected StatusPedidoItem statusPedidoItem;

    public PedidoItem() {
    }

    public PedidoItem(Produto produto, Pedido pedido, Integer quantidade) {
        this.produto = produto;
        this.pedido = pedido;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPedidoItem getStatusPedido() {
        return statusPedidoItem;
    }

    public void setStatusPedido(StatusPedidoItem statusPedidoItem) {
        this.statusPedidoItem = statusPedidoItem;
    }
}
