package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.TipoPagamentoEnumEx;

public class TipoPagamentoExCustom extends RuntimeException{
    public TipoPagamentoExCustom(TipoPagamentoEnumEx tipoErro){
        super(tipoErro.getMensagem());
    }
}
