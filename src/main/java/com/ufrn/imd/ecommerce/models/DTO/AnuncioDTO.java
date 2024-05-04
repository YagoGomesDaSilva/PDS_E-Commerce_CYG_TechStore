package com.ufrn.imd.ecommerce.models.DTO;

import com.ufrn.imd.ecommerce.models.entidades.Anuncio;
import com.ufrn.imd.ecommerce.models.entidades.Imagem;
import com.ufrn.imd.ecommerce.models.entidades.Produto;

public class AnuncioDTO {

    private Imagem imagem;
    private Produto produto;
    private Anuncio anuncio;

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }
}
