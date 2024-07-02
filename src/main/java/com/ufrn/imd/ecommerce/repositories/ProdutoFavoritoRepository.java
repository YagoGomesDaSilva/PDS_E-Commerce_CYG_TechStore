package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.models.entidades.ProdutoFavorito;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoFavoritoRepository extends JpaRepository<ProdutoFavorito, Long> {
    List<ProdutoFavorito> findByUsuario(Usuario usuario);

    List<ProdutoFavorito> findByProduto(Produto produto);

    void deleteByUsuarioAndProduto(Usuario usuario, Produto produto);

    Optional<ProdutoFavorito> findByUsuarioAndProduto(Usuario usuario, Produto produto);
}