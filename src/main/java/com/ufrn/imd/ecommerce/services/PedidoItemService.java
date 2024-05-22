package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.PedidoItemEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.PedidoItemExCustom;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import com.ufrn.imd.ecommerce.repositories.PedidoItemRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoItemService {
    public final PedidoItemRepository pedidoItemRepository;

    public PedidoItemService(PedidoItemRepository pedidoItemRepository) {
        this.pedidoItemRepository = pedidoItemRepository;
    }

    public void createPedidoItem(PedidoItem pedidoItem){
        pedidoItemRepository.save(pedidoItem);
    }

    public void updatePedidoItem(PedidoItem pedidoItem){
        if(pedidoItemRepository.findById(pedidoItem.getId()).isPresent()){
            //Implementar update PedidoItem
        } else {
            throw new PedidoItemExCustom(PedidoItemEnumEx.PEDIDO_ITEM_NAO_ENCONTRADO);
        }
    }

    public void deletePedidoItem(PedidoItem pedidoItem){
        if(pedidoItemRepository.findById(pedidoItem.getId()).isPresent()){
            pedidoItemRepository.delete(pedidoItem);
        } else {
            throw new PedidoItemExCustom(PedidoItemEnumEx.PEDIDO_ITEM_NAO_ENCONTRADO);
        }
    }
}
