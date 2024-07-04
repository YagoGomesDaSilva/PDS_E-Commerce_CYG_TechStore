package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.PagamentoEnumEx;

public class PagamentoExCustom extends RuntimeException{

    public PagamentoExCustom(PagamentoEnumEx message){
        super(message.getMensagem());
    }
}
