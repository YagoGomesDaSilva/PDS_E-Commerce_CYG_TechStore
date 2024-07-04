package com.ufrn.imd.ecommerce.error.exceptions;

import com.ufrn.imd.ecommerce.error.enunsEx.CreditoEnumEx;

public class CreditoExCustom extends RuntimeException {
    public CreditoExCustom(CreditoEnumEx message) {
        super(message.getMensagem());
    }
}
