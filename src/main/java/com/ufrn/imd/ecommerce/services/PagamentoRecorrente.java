package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import com.ufrn.imd.ecommerce.services.interfaces.PagamentoService;

import java.util.List;

public class PagamentoRecorrente implements PagamentoService {
    @Override
    public void repassarPagamento(Pedido pedido, List<PedidoItem> itens) {

    }
}
