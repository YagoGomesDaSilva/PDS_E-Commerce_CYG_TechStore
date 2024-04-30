package com.ufrn.imd.ecommerce.error.enunsEx;

public enum EnderecoEnumEx {
    ENDERECO_NAO_ENCONTRADO("O endereço especificado não foi encontrado."),
    CEP_INVALIDO("O CEP fornecido é inválido."),
    ENDERECO_ENTREGA_NAO_ESPECIFICADO("O endereço de entrega não foi especificado."),
    ENDERECO_INVALIDO_PARA_ENTREGA("O endereço especificado não é válido para entrega."),
    ERRO_SALVAR_ENDERECO("Ocorreu um erro ao salvar o endereço.");

    private String mensagem;

    EnderecoEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

}
