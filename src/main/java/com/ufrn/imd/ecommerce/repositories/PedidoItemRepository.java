package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    @Query("select pi from PedidoItem pi join Pedido p on pi.pedido.id = p.id " +
            "where p.usuario.id = :idUsuario and pi.statusPedidoItem = :statusPedidoItem")
    List<PedidoItem> findAllByUsuarioAndStatus(Long idUsuario, StatusPedidoItem statusPedidoItem);

    Optional<PedidoItem> findByPedidoAndProduto(Pedido pedido, Produto produto);
}
