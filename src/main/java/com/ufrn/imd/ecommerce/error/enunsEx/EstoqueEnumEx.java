package com.ufrn.imd.ecommerce.error.enunsEx;

public enum EstoqueEnumEx {

    ESTOQUE_NAO_ENCONTRADO("Estoque não encontrado para o produto."),
    QUANTIDADE_INVALIDA("A quantidade especificada para o estoque é inválida."),
    PRODUTO_NAO_ASSOCIADO("O produto especificado não está associado a nenhum registro de estoque."),
    OPERACAO_SAIDA_INVALIDA("Não há quantidade suficiente em estoque para realizar a operação de saída."),
    OPERACAO_ENTRADA_INVALIDA("A quantidade especificada para a operação de entrada de estoque é inválida."),
    ESTOQUE_MINIMO_NAO_ALCANCADO("O estoque mínimo definido para o produto não foi alcançado."),
    ESTOQUE_MAXIMO_EXCEDIDO("O estoque máximo definido para o produto foi excedido."),
    ESTOQUE_JA_EXISTENTE("Estoque ja existe!");
    private String mensagem;

    EstoqueEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }


}
