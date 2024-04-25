package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.PedidoEnumEx;

public class PedidoExCustom extends RuntimeException {

    public PedidoExCustom(PedidoEnumEx tipoErro) {
        super(tipoErro.getMensagem());
    }
}