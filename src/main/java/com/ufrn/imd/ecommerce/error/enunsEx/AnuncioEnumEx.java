package com.ufrn.imd.ecommerce.error.enunsEx;

public enum AnuncioEnumEx {
    ANUNCIO_NAO_ENCONTRADO("O anúncio especificado não foi encontrado."),
    ANUNCIO_EXPIRADO("O anúncio especificado está expirado."),
    LIMITE_ANUNCIOS_EXCEDIDO("O limite máximo de anúncios foi excedido."),
    ERRO_PUBLICAR_ANUNCIO("Ocorreu um erro ao publicar o anúncio."),
    NENHUM_ANUNCIO_ENCONTRADO("No momento não há nenhum anúncio cadastrado"),
    ANUNCIO_DESATIVADO("O anúncio está desativado."),
    ANUNCIO_SEM_TITULO("Não é possível cadastrar anúncio sem título!");

    private String mensagem;

    AnuncioEnumEx(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

}
