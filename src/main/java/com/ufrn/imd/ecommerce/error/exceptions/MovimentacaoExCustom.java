package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.MovimentacaoEnumEx;

public class MovimentacaoExCustom extends RuntimeException{
    public MovimentacaoExCustom(MovimentacaoEnumEx tipoErro){
        super(tipoErro.getMensagem());
    }
}
