package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dataEmissao;
    @Column(columnDefinition = "VARCHAR(14)")
    private String numDocumento;
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dataEntrada;
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dataSaida;
    @Column(columnDefinition = "VARCHAR(7)")
    private String tipoMovimentacao;

    public MovimentacaoEstoque() {
    }
}
