package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;

public class EstoqueExCustom extends RuntimeException {
    public EstoqueExCustom(EstoqueEnumEx tipoErro){
        super(tipoErro.getMensagem());
    }
}
