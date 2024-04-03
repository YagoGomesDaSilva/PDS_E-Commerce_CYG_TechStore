package com.ufrn.imd.ecommerce.models.entidades;

import com.ufrn.imd.ecommerce.models.entidadeEnum.TipoPagamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(scale = 2)
    private BigDecimal valorTotal;
    @Column(scale = 2)
    private BigDecimal valorFrete;
    @Column(scale = 2)
    private BigDecimal valorTotalItens;
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate data;
    @Column(columnDefinition = "VARCHAR(20)")
    private String situacao;
    @Column
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    public Pedido() {
    }
}
