package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.EnderecoEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;

public class EnderecoExCustom extends RuntimeException {
    public EnderecoExCustom(EnderecoEnumEx tipoErro){
        super(tipoErro.getMensagem());
    }
}
