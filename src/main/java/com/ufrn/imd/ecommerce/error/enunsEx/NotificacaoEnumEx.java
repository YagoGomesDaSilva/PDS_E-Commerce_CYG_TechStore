package com.ufrn.imd.ecommerce.error.enunsEx;

public enum NotificacaoEnumEx {
    NOTIFICAO_JA_CADASTRADA("O usuário já solicitou que fosse notificado quando o produto voltar ao estoque!");


    private String mensagem;

    NotificacaoEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
