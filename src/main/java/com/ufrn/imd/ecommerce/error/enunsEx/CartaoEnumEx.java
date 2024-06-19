package com.ufrn.imd.ecommerce.error.enunsEx;

public enum CartaoEnumEx {
    CARTAO_NAO_ENCONTRADO("O cartão especificado não foi encontrado."),
    NUMERO_CARTAO_INVALIDO("O número do cartão de crédito fornecido é inválido."),
    CARTAO_EXPIRADO("O cartão especificado está expirado."),
    CVV_INVALIDO("O código CVV fornecido é inválido."),
    CARTAO_BLOQUEADO("O cartão está bloqueado e não pode ser utilizado."),
    LIMITE_CREDITO_EXCEDIDO("O limite de crédito disponível no cartão foi excedido."),
    ERRO_PROCESSAR_PAGAMENTO("Ocorreu um erro ao processar o pagamento com o cartão.");

    private String mensagem;

    CartaoEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }


}
