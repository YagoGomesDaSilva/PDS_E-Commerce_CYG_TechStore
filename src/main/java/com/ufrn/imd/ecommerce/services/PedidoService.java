package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.PagamentoEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.PedidoEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.*;
import com.ufrn.imd.ecommerce.models.DTO.ItemPorAnuncianteDTO;
import com.ufrn.imd.ecommerce.models.DTO.PagamentoDTO;
import com.ufrn.imd.ecommerce.models.DTO.PedidoResponseDTO;
import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.*;
import com.ufrn.imd.ecommerce.services.interfaces.DescontoService;
import com.ufrn.imd.ecommerce.services.interfaces.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import java.util.*;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoItemRepository pedidoItemRepository;
    @Autowired
    @Qualifier("descontoPorItemService")
    private DescontoService descontoService;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private DescontoRepository descontoRepository;
    @Autowired
    @Qualifier("pagamentoComum")
    private PagamentoService pagamentoService;
    @Autowired
    private EnderecoRepository enderecoRepository;



    @Transactional
    public Pedido realizarPedido(List<PedidoItem> itens, Pedido pedido) throws EstoqueExCustom {
        Map<Anunciante, Double> valorPorAnunciante = new HashMap<Anunciante, Double>();
        Double valorTotal = 0.0;

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

            valorTotal += valorTotalItem;

            item.setStatusPedido(StatusPedidoItem.AGUARDANDO_PAGAMENTO);
            pedidoItemRepository.save(item);
        }

        descontoService.calcularDesconto(valorPorAnunciante, pedido);

        Double valorFrete = calcularFrete(enderecoRepository.findByUsuario(pedido.getUsuario()).getEstado());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setValorTotal(valorTotal);
        pedido.setValorFrete(valorFrete);
        return pedidoRepository.save(pedido);
    }

    public Double calcularFrete(String estado) {
        if(estado.equals("RN") || estado.equals("PB") || estado.equals("PE") || estado.equals("AL")
                || estado.equals("CE") || estado.equals("MA") || estado.equals("PI") || estado.equals("BA") || estado.equals("SE") ){
            return 30.00;
        } else if (estado.equals("RS") || estado.equals("SC") || estado.equals("PR")){
            return 60.00;
        } else if (estado.equals("SP") || estado.equals("RJ") || estado.equals("MG") || estado.equals("ES")){
            return 50.00;
        } else if (estado.equals("DF") || estado.equals("GO") || estado.equals("MT") || estado.equals("MS")) {
            return 40.00;
        } else if (estado.equals("AM") || estado.equals("RR") || estado.equals("AP") || estado.equals("PA")
                || estado.equals("TO") || estado.equals("RO") || estado.equals("AC")) {
            return 35.00;
        } else {
            return 70.00;
        }
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
        Double descontoTotal = 0.0;

        for(PedidoItem item : itens) {
            Anunciante anunciante = item.getProduto().getAnunciante();
            Optional<ItemPorAnuncianteDTO> itemPorAnuncianteDTO = pedidoResponseDTO.find(anunciante.getId());

            if(itemPorAnuncianteDTO.isPresent()){
                itemPorAnuncianteDTO.get().addItem(item);
            } else {
                ItemPorAnuncianteDTO itemPorAnunciante = new ItemPorAnuncianteDTO();
                itemPorAnunciante.setIdAnunciante(anunciante.getId());
                itemPorAnunciante.setNomeAnunciante(anunciante.getNomeAnunciante());
                itemPorAnunciante.addItem(item);

                Optional<Desconto> desconto = descontoRepository.findByPedidoAndAnunciante(pedido, anunciante);
                if(desconto.isPresent()){
                    itemPorAnunciante.setDesconto(desconto.get().getValorDesconto());
                    descontoTotal += desconto.get().getValorDesconto();
                }
                pedidoResponseDTO.getItensAnunciante().add(itemPorAnunciante);
            }
        }

        pedidoResponseDTO.setValorTotal(pedido.getValorTotal());
        pedidoResponseDTO.setValorFrete(pedido.getValorFrete());

        pedidoResponseDTO.setValorTotalComFrete(pedido.getValorTotal() + pedido.getValorFrete());
        pedidoResponseDTO.setValorTotalComDesconto(pedido.getValorTotal() + pedido.getValorFrete() - descontoTotal);

        pedidoResponseDTO.setIdPedido(pedido.getId());

        return pedidoResponseDTO;
    }

    public Pedido findPedidoByUsuario(Long idUsuario, StatusPedido statusPedido) throws PedidoExCustom {
        Optional<Pedido> pedido = pedidoRepository.findPedidoByUsuarioAndStatusPeido(idUsuario, statusPedido);
        if(!pedido.isPresent()){
            throw new PedidoExCustom(PedidoEnumEx.PEDIDO_NAO_ENCONTRADO);
        }

        return pedido.get();
    }

    @Transactional
    public Double realizarPagamento(Pedido pedido, List<PedidoItem> itens, PagamentoDTO pagamento) {
        Double valorPagamento = pagamento.getValorPagamento();
        Double desconto = descontoRepository.sumAllDescontosByPedido(pedido.getId());
        Double valorTotalAPagar = pedido.getValorFrete() + pedido.getValorTotal() - desconto;
        if(valorTotalAPagar > valorPagamento){
            throw new PagamentoExCustom(PagamentoEnumEx.VALOR_INVALIDO);
        }
        valorPagamento -= valorTotalAPagar;
        pedido.setStatusPedido(StatusPedido.FINALIZADO);
        for(PedidoItem item : itens) {
            item.setStatusPedido(StatusPedidoItem.FINALIZADO);
        }
        pedidoItemRepository.saveAll(itens);
        pedidoRepository.save(pedido);

        pagamentoService.repassarPagamento(pedido, itens);

        return valorPagamento;
    }
}
