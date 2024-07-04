package com.ufrn.imd.ecommerce.error.enunsEx;

public enum PagamentoEnumEx {
    VALOR_INVALIDO("Saldo insuficiente para realizar o pagamento");

    private String mensagem;

    PagamentoEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }
    public String getMensagem(){
        return mensagem;
    }
}
