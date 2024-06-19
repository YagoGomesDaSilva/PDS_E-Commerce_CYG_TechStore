package com.ufrn.imd.ecommerce.error.enunsEx;

public enum MovimentacaoEnumEx {
    MOVIMENTACAO_NAO_ENCONTRADA("A movimentação especificada não foi encontrada.");

    private String mensagem;

    MovimentacaoEnumEx(String mensagem){
        this.mensagem = mensagem;
    }
    public String getMensagem(){
        return mensagem;
    }
}
