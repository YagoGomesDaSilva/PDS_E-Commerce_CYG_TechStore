package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.models.entidades.ProdutoFavorito;
import com.ufrn.imd.ecommerce.models.entidades.Cliente;
import com.ufrn.imd.ecommerce.repositories.ProdutoFavoritoRepository;
import com.ufrn.imd.ecommerce.repositories.ProdutoRepository;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoFavoritoService {

    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoFavoritoRepository produtoFavoritoRepository;

    public ProdutoFavoritoService(UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository, ProdutoFavoritoRepository produtoFavoritoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.produtoFavoritoRepository = produtoFavoritoRepository;
    }


    public void addFavoriteProduct(Long userId, Long productId) {
        Cliente usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Produto produto = produtoRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        if (!produtoFavoritoRepository.findByClienteAndProduto(usuario, produto).isPresent()) {
            ProdutoFavorito favorito = new ProdutoFavorito();
            favorito.setUsuarioConcreto(usuario);
            favorito.setProduto(produto);

            produtoFavoritoRepository.save(favorito);
        }
    }

    public void removeFavoriteProduct(Long userId, Long productId) {
        Cliente usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Produto produto = produtoRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        produtoFavoritoRepository.deleteByClienteAndProduto(usuario, produto);
    }

    public List<Produto> getFavoriteProducts(Cliente usuario) {
        return produtoFavoritoRepository.findByCliente(usuario).stream()
                .map(ProdutoFavorito::getProduto)
                .collect(Collectors.toList());
    }
}