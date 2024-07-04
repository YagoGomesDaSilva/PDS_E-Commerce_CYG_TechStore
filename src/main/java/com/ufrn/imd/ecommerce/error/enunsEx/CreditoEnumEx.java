package com.ufrn.imd.ecommerce.error.enunsEx;

public enum CreditoEnumEx {
    VALOR_INVALIDO("Não é possível inserir crédito negativo");

    private String mensagem;

    CreditoEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }
    public String getMensagem(){
        return mensagem;
    }
}
