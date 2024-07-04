package com.ufrn.imd.ecommerce.services.interfaces;

import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PagamentoService {
    public void repassarPagamento(Pedido pedido, List<PedidoItem> itens);
}
