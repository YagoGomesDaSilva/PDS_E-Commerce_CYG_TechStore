package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.Entity;

@Entity
public class PedidoItemAssinatura extends PedidoItem{

    private boolean devolvido;



    public PedidoItemAssinatura(PedidoItem item) {
        this.pedido = item.getPedido();
        this.id = item.getId();
        this.produto = item.getProduto();
        this.quantidade = item.getQuantidade();
        this.statusPedidoItem = item.getStatusPedido();
    }

    public PedidoItemAssinatura() {

    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }
}
