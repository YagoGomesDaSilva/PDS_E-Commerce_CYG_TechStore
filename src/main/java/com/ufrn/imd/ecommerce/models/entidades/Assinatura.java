package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Assinatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pedido_assinatura", referencedColumnName = "id")
    private PedidoAssinatura pedidoAssinatura;

    @Temporal(TemporalType.DATE)
    private LocalDate proximaDataRenovacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PedidoAssinatura getPedidoAssinatura() {
        return pedidoAssinatura;
    }

    public void setPedidoAssinatura(PedidoAssinatura pedidoAssinatura) {
        this.pedidoAssinatura = pedidoAssinatura;
    }

    public LocalDate getProximaDataRenovacao() {
        return proximaDataRenovacao;
    }

    public void setProximaDataRenovacao(LocalDate proximaDataRenovacao) {
        this.proximaDataRenovacao = proximaDataRenovacao;
    }
}
