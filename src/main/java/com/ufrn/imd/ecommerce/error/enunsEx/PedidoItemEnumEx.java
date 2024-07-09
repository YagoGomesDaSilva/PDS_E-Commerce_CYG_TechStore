package com.ufrn.imd.ecommerce.error.enunsEx;

public enum PedidoItemEnumEx {
    PEDIDO_ITEM_NAO_ENCONTRADO("O PedidoItem não foi encontrado."),
    ITEM_JA_ESTA_NO_CARRINHO("O item selecionado já está no carrinho!");

    private String mensagem;

    PedidoItemEnumEx(String mensagem){
        this.mensagem = mensagem;
    }
    public String getMensagem(){
        return mensagem;
    }
}
