package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.AnuncioEnumEx;

public class AnuncioExCustom extends RuntimeException {

    public AnuncioExCustom(AnuncioEnumEx tipoErro) {
        super(tipoErro.getMensagem());
    }
}