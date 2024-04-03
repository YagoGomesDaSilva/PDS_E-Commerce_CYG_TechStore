package com.ufrn.imd.ecommerce.models.heranca;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_perfil", discriminatorType = DiscriminatorType.STRING)
public class Anunciante extends UsuarioAbstrato{

    @Column(columnDefinition = "VARCHAR(14)")
    String documento;

    public Anunciante() {
        super();
    }

    public Anunciante(String documento) {
        super();
        this.documento = documento;
    }
}
