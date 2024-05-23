package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.ProdutoFavorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoFavoritoRepository extends JpaRepository<ProdutoFavorito, Long> {
    List<ProdutoFavorito> findByUserId(Long userId);
    List<ProdutoFavorito> findByProductId(Long productId);
    void deleteByUserIdAndProductId(Long userId, Long productId);

    Optional<Object> findByUserIdAndProductId(Long userId, Long productId);
}