package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "VARCHAR(16)")
    private String numero;
    @Column(columnDefinition = "VARCHAR(40)")
    private String nomeTitular;
    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dataVencimento;
    @Column(columnDefinition = "VARCHAR(4)")
    private String codigoSeguranca;

    public Cartao() {
    }
}
