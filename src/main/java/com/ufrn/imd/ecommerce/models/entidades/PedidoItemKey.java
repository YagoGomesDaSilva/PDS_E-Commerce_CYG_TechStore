package com.ufrn.imd.ecommerce.models.entidades;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PedidoItemKey implements Serializable {

    @Column(name = "produto_id")
    private Long produtoId;

    @Column(name = "pedido_id")
    private Long pedidoId;

    public PedidoItemKey() {
    }

    public PedidoItemKey(Long produtoId, Long pedidoId) {
        this.produtoId = produtoId;
        this.pedidoId = pedidoId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoItemKey that = (PedidoItemKey) o;
        return Objects.equals(produtoId, that.produtoId) && Objects.equals(pedidoId, that.pedidoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produtoId, pedidoId);
    }
}
