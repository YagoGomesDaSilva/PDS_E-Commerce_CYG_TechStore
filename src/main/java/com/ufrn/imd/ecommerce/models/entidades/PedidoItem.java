package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

@Entity
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long quantidade;

    public PedidoItem() {
    }
}