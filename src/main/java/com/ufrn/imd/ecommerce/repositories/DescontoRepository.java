package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Desconto;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescontoRepository extends JpaRepository<Desconto, Long> {
    Desconto findByPedidoAndAnunciante(Pedido pedido, Anunciante anunciante);
}
