package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.CategoriaEnumEx;

public class CategoriaExCustom extends RuntimeException{
    public CategoriaExCustom(CategoriaEnumEx tipoErro){
        super(tipoErro.getMensagem());
    }
}
