package com.ufrn.imd.ecommerce.error.enunsEx;

public enum ProdutoEnumEx {
    PRODUTO_INVALIDO("Produto possui informações inválidas."),
    PRODUTO_NAO_ENCONTRADO("O produto não foi encontrado."),
    CATEGORIA_NAO_ESPECIFICADA("A categoria do produto não foi especificada."),
    PRODUTO_VALOR_NEGATIVO("Não é possível cadastrar produto com valor negativo.");

    private String mensagem;

    ProdutoEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
