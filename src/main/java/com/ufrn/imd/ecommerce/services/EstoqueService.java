package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.ProdutoEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.EstoqueExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.ProdutoExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Estoque;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.repositories.EstoqueRepository;
import com.ufrn.imd.ecommerce.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstoqueService {
/*
    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    public Estoque atualizarEstoque(Long idProduto, int quantidade) {
        Optional<Produto> produtoOptional = produtoRepository.findById(idProduto);
        Produto produto = produtoOptional.orElseThrow(() -> new ProdutoExCustom(ProdutoEnumEx.PRODUTO_NAO_ENCONTRADO));

        Estoque estoque = estoqueRepository.findByProdutoId(idProduto)
                .orElseGet(() -> criarEstoqueParaProduto(produto));

        int novaQuantidade = estoque.getQuantidade() + quantidade;
        if (novaQuantidade < 0) {
            throw new EstoqueExCustom(EstoqueEnumEx.QUANTIDADE_INVALIDA);
        }

        estoque.setQuantidade(novaQuantidade);
        return estoqueRepository.save(estoque);
    }

    private Estoque criarEstoqueParaProduto(Produto produto) {
        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setQuantidade(0); // Inicialmente, o estoque é zero para um novo produto
        return estoqueRepository.save(estoque);
    }

    public int verificarQuantidadeEmEstoque(Long idProduto) {
        return estoqueRepository.findByProdutoId(idProduto)
                .map(Estoque::getQuantidade)
                .orElse(0); // Se o produto não tiver um estoque registrado, retorna 0
    }
    */
}

