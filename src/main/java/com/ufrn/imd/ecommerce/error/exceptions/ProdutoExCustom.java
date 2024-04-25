package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.PedidoEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.ProdutoEnumEx;

public class ProdutoExCustom extends RuntimeException {

    public ProdutoExCustom(ProdutoEnumEx tipoErro) {
        super(tipoErro.getMensagem());
    }
}