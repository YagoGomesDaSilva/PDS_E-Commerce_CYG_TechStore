package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.Pedido;
import com.ufrn.imd.ecommerce.models.UsuarioConcreto;
import com.ufrn.imd.ecommerce.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido findPedido(Long idPedido) throws Exception {
        Pedido pedido = pedidoRepository.findById(idPedido).isPresent() ? pedidoRepository.findById(idPedido).get() : null;
        if(pedido == null){
            throw new Exception();
        }
        return pedido;
    }

    public List<Pedido> findPedidos() throws Exception {
        List<Pedido> pedidos = pedidoRepository.findAll();
        if(pedidos.isEmpty()){
            throw new Exception();
        }
        return pedidos;
    }


    public void createPedido(Pedido pedido){
        // to-do validações para criação do pedido;
        pedidoRepository.save(pedido);
    }

    private void updadePedido(Pedido pedido) throws Exception{
        if(pedidoRepository.findById(pedido.getId()).isPresent()){
            //to-do implementar update em Usuario
        } else {
            throw new Exception("Pedido não encontrado");
        }
    }

    public void deletarUsuario(Pedido pedido) throws Exception{
        if(pedidoRepository.findById(pedido.getId()).isPresent()){
            pedidoRepository.delete(pedido);
        } else {
            throw new Exception("Usuario não encontrado");
        }
    }

}
