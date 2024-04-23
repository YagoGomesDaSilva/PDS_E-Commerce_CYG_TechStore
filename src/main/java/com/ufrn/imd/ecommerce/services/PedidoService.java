package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.DTO.PedidoDTO;
import com.ufrn.imd.ecommerce.models.DTO.PedidoItemDTO;
import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.PedidoItemRepository;
import com.ufrn.imd.ecommerce.repositories.PedidoRepository;
import com.ufrn.imd.ecommerce.repositories.ProdutoRepository;
import com.ufrn.imd.ecommerce.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, PedidoItemRepository pedidoItemRepository , UsuarioRepository usuarioRepository,ProdutoRepository produtoRepository){
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }


    @Transactional
    public Pedido createPedido(PedidoDTO dto) throws Exception {
        Long compradorId = dto.getCompradorId();

        UsuarioConcreto usuario = usuarioRepository.findById(compradorId).orElseThrow(() -> new Exception("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setData(LocalDate.now());
        pedido.setUsuario(usuario);

        Set<PedidoItem> pedidoItems = converterItems(pedido, dto.getItems());
        pedidoRepository.save(pedido);
        pedidoItemRepository.saveAll(pedidoItems);
        pedido.setPedidoItems(pedidoItems);
        return pedido;
    }

    private Set<PedidoItem> converterItems(Pedido pedido, List<PedidoItemDTO> items) throws Exception {
        if(items.isEmpty()){
            throw new Exception("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Long idProduto = dto.getProduto();
                    Produto produto = null;
                    try {
                        produto = produtoRepository
                                .findById(idProduto)
                                .orElseThrow(() -> new Exception( "Código de produto inválido: "+ idProduto ));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    PedidoItem pedidoItems = new PedidoItem();
                    pedidoItems.setQuantidade(dto.getQuantidade());
                    pedidoItems.setPedido(pedido);
                    pedidoItems.setProduto(produto);
                    return pedidoItems;
                }).collect(Collectors.toSet());
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
