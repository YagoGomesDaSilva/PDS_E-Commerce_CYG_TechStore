package com.ufrn.imd.ecommerce.error.enunsEx;

public enum UsuarioEnumEx {
    USUARIO_NAO_ENCONTRADO("O usuário informado não foi encontrado"),
    EMAIL_USUARIO_NAO_ENCONTRADO("O email informado não foi encontrado"),
    NOME_USUARIO_INVALIDO("O nome inserido para o usuário possui caracteres numéricos ou carácteres especiais"),
    EMAIL_DUPLICADO("Já existe um usuário cadastrado com esse email em nosso sistema"),
    SEM_USUARIOS_CADASTRADOS("Não há usuários cadastrados no sistema");

    private String mensagem;

    UsuarioEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
