package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    Optional<Anuncio> findAnuncioById(Long idAnuncio);

    List<Anuncio> findAllByAnunciante(Anunciante anunciante);
}
