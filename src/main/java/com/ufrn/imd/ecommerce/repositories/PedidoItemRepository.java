package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    @Query("select pi from PedidoItem pi join Pedido p on pi.pedido.id = p.id " +
            "where p.usuario.id = :idUsuario and pi.statusPedidoItem = :statusPedidoItem")
    List<PedidoItem> findAllByUsuarioAndStatus(Long idUsuario, StatusPedidoItem statusPedidoItem);
}
