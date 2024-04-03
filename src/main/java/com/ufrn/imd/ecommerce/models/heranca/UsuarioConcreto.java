package com.ufrn.imd.ecommerce.models.heranca;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("usuario")
public  class UsuarioConcreto extends UsuarioAbstrato{

    public UsuarioConcreto() {
        super();
    }
}
