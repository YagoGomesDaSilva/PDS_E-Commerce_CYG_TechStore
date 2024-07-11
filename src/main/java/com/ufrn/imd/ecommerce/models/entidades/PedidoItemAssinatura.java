package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.Entity;

@Entity
public class PedidoItemAssinatura extends PedidoItem{

    private boolean devolvido;

    public boolean isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }
}
