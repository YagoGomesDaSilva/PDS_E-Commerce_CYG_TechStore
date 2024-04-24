package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Optional<Produto> findProduto(Long idProduto) throws Exception {
        Produto produto = produtoRepository.findById(idProduto).isPresent() ? produtoRepository.findById(idProduto).get() : null;

        if(produto == null){
            throw new Exception("Produto não encontrado");
        }
        return Optional.of(produto);
    }

    public List<Produto> findProdutos() throws Exception {
        List<Produto> produtos = produtoRepository.findAll();
        if(produtos.isEmpty()){
            throw new Exception();
        }
        return  produtos;
    }

    public Produto createProduto(Produto produto) {
        //to-do validações para criação do produto
        return produtoRepository.save(produto);
    }

    public void updateProduto(Produto produto) throws Exception {
        if(produtoRepository.findById(produto.getId()).isPresent()){
            //to-do implementar update em produto
        } else {
            throw new Exception("Produto não encontrado");
        }
    }

    public void deletarProduto(Produto produto) throws Exception{
        if(produtoRepository.findById(produto.getId()).isPresent()){
            produtoRepository.delete(produto);
        } else {
            throw new Exception("Produto não encontrado");
        }
    }

}
