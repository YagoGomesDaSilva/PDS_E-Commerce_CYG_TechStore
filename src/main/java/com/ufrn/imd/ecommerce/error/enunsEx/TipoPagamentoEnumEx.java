package com.ufrn.imd.ecommerce.error.enunsEx;

public enum TipoPagamentoEnumEx {
    TIPO_PAGAMENTO_NAO_ENCONTRADO("O tipo de pagamento especificado não foi encontrado.");

    private String mensagem;

    TipoPagamentoEnumEx(String mensagem){
        this.mensagem = mensagem;
    }
    public String getMensagem(){
        return mensagem;
    }
}
