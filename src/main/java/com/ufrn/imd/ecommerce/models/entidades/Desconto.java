package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

@Entity
public class Desconto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorDesconto;

    @ManyToOne
    @JoinColumn(name = "id_anunciante", referencedColumnName = "id")
    private Anunciante anunciante;

    @ManyToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    private Pedido pedido;

    public Desconto() {
        this.valorDesconto = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Anunciante getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(Anunciante anunciante) {
        this.anunciante = anunciante;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
