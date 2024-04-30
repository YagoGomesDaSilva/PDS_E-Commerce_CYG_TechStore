package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;

public class UsuarioExCustom  extends RuntimeException {

    public UsuarioExCustom(UsuarioEnumEx tipoErro){
        super();
    }

    public UsuarioExCustom(String message){
        super(message);
    }
}
