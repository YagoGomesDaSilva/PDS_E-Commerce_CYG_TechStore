package com.ufrn.imd.ecommerce.models.DTO;

import com.ufrn.imd.ecommerce.models.entidades.Imagem;
import com.ufrn.imd.ecommerce.models.entidades.Produto;

public class ProdutoImagemDTO {

    Produto produto;

    Imagem imagem;

    public ProdutoImagemDTO() {
    }

    public ProdutoImagemDTO(Produto produto, Imagem imagem) {
        this.produto = produto;
        this.imagem = imagem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }
}
