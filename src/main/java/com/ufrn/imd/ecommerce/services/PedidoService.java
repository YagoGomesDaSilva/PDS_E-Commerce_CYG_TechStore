package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.PedidoEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.ProdutoEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.*;
import com.ufrn.imd.ecommerce.models.DTO.PedidoDTO;
import com.ufrn.imd.ecommerce.models.DTO.PedidoItemDTO;
import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.*;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    private final EstoqueRepository estoqueRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         PedidoItemRepository pedidoItemRepository,
                         UsuarioRepository usuarioRepository,
                         ProdutoRepository produtoRepository,
                         EstoqueRepository estoqueRepository){

        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public Pedido createPedido(PedidoDTO dto, UsuarioConcreto usuario)  {
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

    private Set<PedidoItem> converterItems(Pedido pedido, List<PedidoItemDTO> items)  {
        if(items.isEmpty()){
            throw new ProdutoExCustom(ProdutoEnumEx.PRODUTO_NAO_ENCONTRADO);
        }

        return items
                .stream()
                .map( dto -> {
                    Long idProduto = dto.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(() -> new ProdutoExCustom(ProdutoEnumEx.PRODUTO_INVALIDO));

                    int qtdProduto = produto.getEstoques().stream()
                            .mapToInt(Estoque::getQuantidade).sum();

                    if (qtdProduto < dto.getQuantidade() ) {
                        throw new EstoqueExCustom(EstoqueEnumEx.ESTOQUE_NAO_ENCONTRADO);
                    }

                    // Diminuir a quantidade do produto no estoque

                    Long quantidadeRestante = dto.getQuantidade();
                    for (Estoque estoque : produto.getEstoques()) {
                        int quantidadeNoEstoque = estoque.getQuantidade();

                        if (quantidadeNoEstoque >= quantidadeRestante) {
                            estoque.setQuantidade((int) (quantidadeNoEstoque - quantidadeRestante));
                            estoqueRepository.save(estoque); // Persistir alteração no banco
                            quantidadeRestante = 0L;
                            break;
                        }
                        else {
                            estoque.setQuantidade(0);
                            estoqueRepository.save(estoque); // Persistir alteração no banco
                            quantidadeRestante -= quantidadeNoEstoque;
                            // Continue no loop para subtrair o restante dos próximos estoques
                        }
                    }

                    if(quantidadeRestante>0)
                        throw new EstoqueExCustom(EstoqueEnumEx.QUANTIDADE_INVALIDA);

                    PedidoItem pedidoItems = new PedidoItem();
                    pedidoItems.setQuantidade(dto.getQuantidade());
                    pedidoItems.setPedido(pedido);
                    pedidoItems.setProduto(produto);
                    return pedidoItems;
                }).collect(Collectors.toSet());
    }


    public Pedido findPedido(Long idPedido)  {
        Pedido pedido = pedidoRepository.findById(idPedido).isPresent() ? pedidoRepository.findById(idPedido).get() : null;
        if(pedido == null){
            throw new PedidoExCustom(PedidoEnumEx.PEDIDO_NAO_ENCONTRADO);
        }
        return pedido;
    }
    public List<Pedido> findPedidos()  {
        List<Pedido> pedidos = pedidoRepository.findAll();
        if(pedidos.isEmpty()){
            throw new RegraNegocioException("Não ha pedidos realizados!");
        }
        return pedidos;
    }

    public Optional<Pedido> findFullPedido(Long idPedido){
        return pedidoRepository.findPedidoByIdWithPedidoItemsAndUsuario(idPedido);
    }
    private void updadePedido(Pedido pedido) {
        if(pedidoRepository.findById(pedido.getId()).isPresent()){
            //to-do implementar update em Usuario
        } else {
            throw new PedidoExCustom(PedidoEnumEx.PEDIDO_NAO_ENCONTRADO);
        }
    }
    public void deletarPedido(Pedido pedido) {
        if(pedidoRepository.findById(pedido.getId()).isPresent()){
            pedidoRepository.delete(pedido);
        } else {
            throw new PedidoExCustom(PedidoEnumEx.PEDIDO_NAO_ENCONTRADO);
        }
    }

}
