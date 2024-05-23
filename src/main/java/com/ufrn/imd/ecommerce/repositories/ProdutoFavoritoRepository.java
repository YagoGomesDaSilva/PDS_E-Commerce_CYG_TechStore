package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.models.entidades.ProdutoFavorito;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoFavoritoRepository extends JpaRepository<ProdutoFavorito, Long> {
    List<ProdutoFavorito> findByUsuarioConcreto(UsuarioConcreto usuario);

    List<ProdutoFavorito> findByProduto(Produto produto);

    void deleteByUsuarioConcretoAndProduto(UsuarioConcreto usuario, Produto produto);

    Optional<Object> findByUsuarioConcretoAndProduto(UsuarioConcreto usuario, Produto produto);
}