package com.ufrn.imd.ecommerce.error.enunsEx;

public enum PedidoItemEnumEx {
    PEDIDO_ITEM_NAO_ENCONTRADO("O PedidoItem não foi encontrado.");

    private String mensagem;

    PedidoItemEnumEx(String mensagem){
        this.mensagem = mensagem;
    }
    public String getMensagem(){
        return mensagem;
    }
}
