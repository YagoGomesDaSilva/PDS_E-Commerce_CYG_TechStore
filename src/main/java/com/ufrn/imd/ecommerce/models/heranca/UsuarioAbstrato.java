package com.ufrn.imd.ecommerce.models.heranca;

import jakarta.persistence.*;

import java.math.BigDecimal;

@MappedSuperclass
public abstract class UsuarioAbstrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(50)")
    private String nome;
    @Column(columnDefinition = "VARCHAR(50)")
    private String email;
    @Column(columnDefinition = "VARCHAR(50)")
    private String senha;
    @Column(columnDefinition = "VARCHAR(15)")
    private String numeroTelefone;
    @Column
    private boolean tipo;
    @Column(columnDefinition = "VARCHAR(100)")
    private String token;
    @Column(scale = 2)
    private BigDecimal credito;

    public UsuarioAbstrato() {
    }
}
