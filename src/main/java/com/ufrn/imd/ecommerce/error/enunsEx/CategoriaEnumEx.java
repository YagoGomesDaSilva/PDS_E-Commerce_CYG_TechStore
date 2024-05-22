package com.ufrn.imd.ecommerce.error.enunsEx;

public enum CategoriaEnumEx {

    CATEGORIA_NAO_ENCONTRADA("A categoria especificada não foi encontrada.");
    private String mensagem;
    CategoriaEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }
    public String getMensagem(){
        return mensagem;
    }
}
