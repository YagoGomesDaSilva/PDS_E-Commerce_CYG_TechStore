package com.ufrn.imd.ecommerce.error.enunsEx;

public enum PedidoEnumEx {
    PEDIDO_INVALIDO("O pedido é inválido."),
    PEDIDO_NAO_ENCONTRADO("O pedido não foi encontrado."),
    ITEM_PEDIDO_NAO_ENCONTRADO("O item especificado não foi encontrado."),
    CLIENTE_NAO_ENCONTRADO("O cliente associado ao pedido não foi encontrado.");

    private String mensagem;

    PedidoEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
