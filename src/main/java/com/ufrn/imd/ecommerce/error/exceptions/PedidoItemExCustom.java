package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.PedidoItemEnumEx;

public class PedidoItemExCustom extends RuntimeException{
    public PedidoItemExCustom(PedidoItemEnumEx tipoErro){
        super(tipoErro.getMensagem());
    }
}
