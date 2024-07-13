package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.PedidoAssinatura;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItemAssinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoAssinaturaRepository extends JpaRepository<PedidoAssinatura, Long> {

    @Query("select pi from Usuario u join Pedido p on p.usuario.id = :idUsuario join PedidoItemAssinatura pi on pi.pedido.id = p.id where pi.devolvido")
    List<PedidoItemAssinatura> findItensDevolvidoByUsuario(long idUsuario);
}
