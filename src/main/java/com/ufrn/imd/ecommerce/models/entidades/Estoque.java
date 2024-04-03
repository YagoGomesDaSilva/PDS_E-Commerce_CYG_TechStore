package com.ufrn.imd.ecommerce.models.entidades;

import com.ufrn.imd.ecommerce.models.heranca.UsuarioAbstrato;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(14)")
    private String numDocumento;
    @Column
    private Integer quantidade;
    @Column(columnDefinition = "VARCHAR(20)")
    private String localEstoque;

    public Estoque() {

    }
}
