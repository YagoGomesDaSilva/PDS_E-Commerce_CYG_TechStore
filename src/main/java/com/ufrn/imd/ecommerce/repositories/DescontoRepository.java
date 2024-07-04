package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Desconto;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DescontoRepository extends JpaRepository<Desconto, Long> {

    Optional<Desconto> findByPedidoAndAnunciante(Pedido pedido, Anunciante anunciante);

    @Query("select sum(d.valorDesconto) as valor_desconto from Desconto d where d.pedido.id = :idPedido group by d.pedido.id")
    Double sumAllDescontosByPedido(Long idPedido);
}
