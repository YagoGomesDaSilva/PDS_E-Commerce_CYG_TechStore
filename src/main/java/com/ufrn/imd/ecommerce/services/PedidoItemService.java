package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.error.enunsEx.PedidoItemEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.PedidoItemExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Anuncio;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.repositories.PedidoItemRepository;
import com.ufrn.imd.ecommerce.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoItemService {
    public final PedidoItemRepository pedidoItemRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoItemService(PedidoItemRepository pedidoItemRepository, PedidoRepository pedidoRepository) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.pedidoRepository = pedidoRepository;
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

    public PedidoItem addItemToCart(Usuario usuario, Anuncio anuncio) {
        Optional<Pedido> pedido = pedidoRepository.findPedidoByUsuarioAndStatusPeido(usuario.getId(), StatusPedido.EM_ANDAMENTO);

        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setStatusPedido(StatusPedidoItem.CARRINHO);
        pedidoItem.setProduto(anuncio.getProduto());
        pedidoItem.setQuantidade(1);

        if(!pedido.isPresent()){
            Pedido novoPedido = new Pedido();
            novoPedido.setUsuario(usuario);
            novoPedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);
            novoPedido = pedidoRepository.save(novoPedido);
            pedidoItem.setPedido(novoPedido);
            return pedidoItemRepository.save(pedidoItem);
        }
        pedidoItem.setPedido(pedido.get());
        return pedidoItemRepository.save(pedidoItem);
    }

    public void removeItemToCart(Long idPedidoItem) {
        if(pedidoItemRepository.findById(idPedidoItem).isPresent()){
            pedidoItemRepository.deleteById(idPedidoItem);
        } else {
            throw new PedidoItemExCustom(PedidoItemEnumEx.PEDIDO_ITEM_NAO_ENCONTRADO);
        }
    }

    public List<PedidoItem> findAllItemsInCart(Long idUsuario) {
        return pedidoItemRepository.findAllByUsuarioAndStatus(idUsuario, StatusPedidoItem.CARRINHO);
    }
}
