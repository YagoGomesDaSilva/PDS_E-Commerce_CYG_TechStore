package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.PedidoEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.ProdutoEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.*;
import com.ufrn.imd.ecommerce.models.DTO.ItemPorAnuncianteDTO;
import com.ufrn.imd.ecommerce.models.DTO.PedidoDTO;
import com.ufrn.imd.ecommerce.models.DTO.PedidoItemDTO;
import com.ufrn.imd.ecommerce.models.DTO.PedidoResponseDTO;
import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.*;
import com.ufrn.imd.ecommerce.services.interfaces.DescontoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final DescontoService descontoService;

    private final EstoqueRepository estoqueRepository;
    private final DescontoRepository descontoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         PedidoItemRepository pedidoItemRepository,
                         UsuarioRepository usuarioRepository,
                         ProdutoRepository produtoRepository, @Qualifier("descontoPorAnuncianteService") DescontoService descontoService,
                         EstoqueRepository estoqueRepository, DescontoRepository descontoRepository){

        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.descontoService = descontoService;
        this.estoqueRepository = estoqueRepository;
        this.descontoRepository = descontoRepository;
    }

    @Transactional
    public void realizarPedido(List<PedidoItem> itens, Pedido pedido) throws EstoqueExCustom {
        Map<Anunciante, Double> valorPorAnunciante = new HashMap<Anunciante, Double>();

        for(PedidoItem item : itens){
            Optional <Estoque> estoque = estoqueRepository.findByProduto(item.getProduto().getId());
            if(!estoque.isPresent()){
                throw new EstoqueExCustom(EstoqueEnumEx.ESTOQUE_NAO_ENCONTRADO);
            }
            if(estoque.get().getQuantidade() < item.getQuantidade()){
                throw new EstoqueExCustom(EstoqueEnumEx.OPERACAO_SAIDA_INVALIDA);
            }
            estoque.get().setQuantidade(estoque.get().getQuantidade() - item.getQuantidade());
            estoqueRepository.save(estoque.get());

            Anunciante anunciante = item.getProduto().getAnunciante();
            double valorTotalItem = item.getProduto().getValorTotal() * item.getQuantidade();
            valorPorAnunciante.merge(anunciante, valorTotalItem, Double::sum);

            item.setStatusPedido(StatusPedidoItem.AGUARDANDO_PAGAMENTO);
            pedidoItemRepository.save(item);
        }

        descontoService.calcularDesconto(valorPorAnunciante, pedido);

        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedidoRepository.save(pedido);
    }

//    @Transactional
//    public Pedido createPedido(PedidoDTO dto, Usuario usuario)  {
//        Pedido pedido = new Pedido();
//        pedido.setValorTotal(dto.getValorTotal());
//        pedido.setData(LocalDate.now());
//        pedido.setUsuario(usuario);
//
//        Set<PedidoItem> pedidoItems = converterItems(pedido, dto.getItems());
//        pedidoRepository.save(pedido);
//        pedidoItemRepository.saveAll(pedidoItems);
//        pedido.setPedidoItems(pedidoItems);
//        return pedido;
//    }

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

                    Integer quantidadeRestante = dto.getQuantidade();
                    for (Estoque estoque : produto.getEstoques()) {
                        int quantidadeNoEstoque = estoque.getQuantidade();

                        if (quantidadeNoEstoque >= quantidadeRestante) {
                            estoque.setQuantidade((int) (quantidadeNoEstoque - quantidadeRestante));
                            estoqueRepository.save(estoque); // Persistir alteração no banco
                            quantidadeRestante = 0;
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
        Optional<Pedido> pedido = pedidoRepository.findPedidoByIdWithPedidoItemsAndUsuario(idPedido);
        if(pedido.isPresent()){
            return pedido;
        }
        throw new PedidoExCustom(PedidoEnumEx.PEDIDO_NAO_ENCONTRADO);
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

    public PedidoResponseDTO prepararResposta(List<PedidoItem> itens, Pedido pedido) {
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
        Double valorTotal = 0.0;
        Double descontoTotal = 0.0;

        for(PedidoItem item : itens) {
            Anunciante anunciante = item.getProduto().getAnunciante();
            Optional<ItemPorAnuncianteDTO> itemPorAnuncianteDTO = pedidoResponseDTO.find(anunciante.getId());

            valorTotal += (item.getProduto().getValorTotal() * item.getQuantidade());

            if(itemPorAnuncianteDTO.isPresent()){
                itemPorAnuncianteDTO.get().addItem(item);
            } else {
                ItemPorAnuncianteDTO itemPorAnunciante = new ItemPorAnuncianteDTO();
                itemPorAnunciante.setIdAnunciante(anunciante.getId());
                itemPorAnunciante.addItem(item);

                Desconto desconto = descontoRepository.findByPedidoAndAnunciante(pedido, anunciante);
                itemPorAnunciante.setDesconto(desconto.getValorDesconto());
                pedidoResponseDTO.getItensAnunciante().add(itemPorAnunciante);

                descontoTotal += desconto.getValorDesconto();
            }
        }

        pedidoResponseDTO.setValorTotal(valorTotal);
        pedidoResponseDTO.setValorTotalComDesconto(valorTotal - descontoTotal);

        return pedidoResponseDTO;
    }

    public Pedido findPedidoByUsuario(Long idUsuario) throws PedidoExCustom {
        Optional<Pedido> pedido = pedidoRepository.findPedidoByUsuarioAndStatusPeido(idUsuario, StatusPedido.EM_ANDAMENTO);
        if(!pedido.isPresent()){
            throw new PedidoExCustom(PedidoEnumEx.PEDIDO_NAO_ENCONTRADO);
        }

        return pedido.get();
    }
}
