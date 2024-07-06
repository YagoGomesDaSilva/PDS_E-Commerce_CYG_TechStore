package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.NotificacaoEnumEx;

public class NotificaoExCustom extends Exception{
    public NotificaoExCustom(NotificacaoEnumEx tipoErro){
        super(tipoErro.getMensagem());
    }
}
