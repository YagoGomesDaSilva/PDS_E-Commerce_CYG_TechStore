package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class PedidoItem {

    @EmbeddedId
    private PedidoItemKey id;

    @ManyToOne
    @MapsId("produtoId")
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column
    private Long quantidade;

    public PedidoItem() {
    }

    public PedidoItem(Produto produto, Pedido pedido, Long quantidade) {
        this.produto = produto;
        this.pedido = pedido;
        this.quantidade = quantidade;
    }

    public PedidoItemKey getId() {
        return id;
    }

    public void setId(PedidoItemKey id) {
        this.id = id;
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

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoItem that = (PedidoItem) o;
        return Objects.equals(id, that.id) && Objects.equals(produto, that.produto) && Objects.equals(pedido, that.pedido) && Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produto, pedido, quantidade);
    }
}
