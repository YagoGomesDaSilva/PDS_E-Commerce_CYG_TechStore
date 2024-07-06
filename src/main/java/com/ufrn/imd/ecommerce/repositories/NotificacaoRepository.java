package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.Anuncio;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.models.entidades.Notificacao;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuario(Usuario usuario);

    Optional<Notificacao> findByUsuarioAndAnuncio(Usuario usuario, Anuncio anuncio);

    @Query("select n from Notificacao n where n.anuncio.id = :idAnuncio order by n.horaDaSolicitacao limit :quantidade")
    List<Notificacao> findByProdutoAndQuantidade(Long idAnuncio, int quantidade);
}