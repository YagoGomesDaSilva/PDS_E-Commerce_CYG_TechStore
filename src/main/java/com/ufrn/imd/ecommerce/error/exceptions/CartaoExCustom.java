package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.CartaoEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.EnderecoEnumEx;

public class CartaoExCustom extends RuntimeException {
    public CartaoExCustom(CartaoEnumEx tipoErro){
        super(tipoErro.getMensagem());
    }
}