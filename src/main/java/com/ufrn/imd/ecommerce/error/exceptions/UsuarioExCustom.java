package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;

public class UsuarioExCustom extends Exception {

    public UsuarioExCustom(UsuarioEnumEx usuarioNaoEncontrado){
        super();
    }

    public UsuarioExCustom(String message){
        super(message);
    }
}
