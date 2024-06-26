package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
