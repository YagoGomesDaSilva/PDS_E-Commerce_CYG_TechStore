package com.ufrn.imd.ecommerce.error.enunsEx;

public enum ProdutoEnumEx {
    PRODUTO_INVALIDO("O pedido é inválido."),
    PRODUTO_NAO_ENCONTRADO("O pedido não foi encontrado."),
    CATEGORIA_NAO_ESPECIFICADA("A categoria do produto não foi especificada.");



    private String mensagem;

    ProdutoEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
