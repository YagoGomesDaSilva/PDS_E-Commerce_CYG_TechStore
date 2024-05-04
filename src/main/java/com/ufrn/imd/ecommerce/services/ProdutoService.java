package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.ProdutoEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.ProdutoExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.RegraNegocioException;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.repositories.ProdutoRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Optional<Produto> findProduto(Long idProduto)  {
        Produto produto = produtoRepository.findById(idProduto).isPresent() ? produtoRepository.findById(idProduto).get() : null;
        if(produto == null){
            throw new ProdutoExCustom(ProdutoEnumEx.PRODUTO_NAO_ENCONTRADO);
        }
        return Optional.of(produto);
    }

    public List<Produto> findProdutos()  {
        List<Produto> produtos = produtoRepository.findAll();
        if(produtos.isEmpty()){
            throw new RegraNegocioException("Não ha pedidos realizados!");
        }
        return  produtos;
    }

    public Produto createProduto(Produto produto) {
        //to-do validações para criação do produto
        return produtoRepository.save(produto);
    }

    public Produto updateProduto(Produto produto)  {
        if(produtoRepository.findById(produto.getId()).isPresent()){
            return produtoRepository.save(produto);
        } else {
            throw new ProdutoExCustom(ProdutoEnumEx.PRODUTO_NAO_ENCONTRADO);
        }
    }

    public void deletarProduto(Produto produto) {
        if(produtoRepository.findById(produto.getId()).isPresent()){
            produtoRepository.delete(produto);
        } else {
            throw new ProdutoExCustom(ProdutoEnumEx.PRODUTO_NAO_ENCONTRADO);
        }
    }

    public Produto findProdutoByAnuncio(Long idAnuncio) {
        return new Produto();
    }
}
