package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.PagamentoEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.EstoqueExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.PagamentoExCustom;
import com.ufrn.imd.ecommerce.models.DTO.PagamentoDTO;
import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.*;
import com.ufrn.imd.ecommerce.services.interfaces.DescontoService;
import com.ufrn.imd.ecommerce.services.interfaces.PagamentoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PedidoAssinaturaService extends PedidoService{


    private final EstoqueRepository estoqueRepository;
    private final PedidoAssinaturaRepository pedidoAssinaturaRepository;
    private final EnderecoRepository enderecoRepository;
    private final PedidoItemAssinaturaRepository pedidoItemAssinaturaRepository;
    private final DescontoService descontoService;
    private final AssinaturaRepository assinaturaRepository;
    private final DescontoRepository descontoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoService pagamentoService;

    public PedidoAssinaturaService(EstoqueRepository estoqueRepository, PedidoAssinaturaRepository pedidoAssinaturaRepository,
                                   EnderecoRepository enderecoRepository, PedidoItemAssinaturaRepository pedidoItemAssinaturaRepository,
                                   @Qualifier("descontoAssinaturaService") DescontoService descontoService, AssinaturaRepository assinaturaRepository,
                                   DescontoRepository descontoRepository, PedidoItemRepository pedidoItemRepository, PedidoRepository pedidoRepository,
                                   @Qualifier("pagamentoRecorrente") PagamentoService pagamentoService) {
        super();
        this.estoqueRepository = estoqueRepository;
        this.pedidoAssinaturaRepository = pedidoAssinaturaRepository;
        this.enderecoRepository = enderecoRepository;
        this.pedidoItemAssinaturaRepository = pedidoItemAssinaturaRepository;
        this.descontoService = descontoService;
        this.assinaturaRepository = assinaturaRepository;
        this.descontoRepository = descontoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoService = pagamentoService;
    }

    @Override
    @Transactional
    public Pedido realizarPedido(List<PedidoItem> itens, Pedido pedido) throws EstoqueExCustom {
        Map<Anunciante, Double> valorPorAnunciante = new HashMap<Anunciante, Double>();
        Double valorTotal = 0.0;

        for(PedidoItem item : itens){
            PedidoItemAssinatura itemAssinatura = (PedidoItemAssinatura) item;
            itemAssinatura.setDevolvido(false);

            Optional<Estoque> estoque = estoqueRepository.findByProduto(itemAssinatura.getProduto().getId());
            if(!estoque.isPresent()){
                throw new EstoqueExCustom(EstoqueEnumEx.ESTOQUE_NAO_ENCONTRADO);
            }
            if(estoque.get().getQuantidade() < itemAssinatura.getQuantidade()){
                throw new EstoqueExCustom(EstoqueEnumEx.OPERACAO_SAIDA_INVALIDA);
            }
            estoque.get().setQuantidade(estoque.get().getQuantidade() - itemAssinatura.getQuantidade());
            estoqueRepository.save(estoque.get());

            Anunciante anunciante = itemAssinatura.getProduto().getAnunciante();
            double valorTotalItem = itemAssinatura.getProduto().getValorTotal() * itemAssinatura.getQuantidade();
            valorPorAnunciante.merge(anunciante, valorTotalItem, Double::sum);

            valorTotal += valorTotalItem;

            itemAssinatura.setStatusPedido(StatusPedidoItem.AGUARDANDO_PAGAMENTO);
            pedidoItemAssinaturaRepository.save(itemAssinatura);
        }

        descontoService.calcularDesconto(valorPorAnunciante, pedido);

        Double valorFrete = calcularFrete(enderecoRepository.findByUsuario(pedido.getUsuario()).getEstado());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setValorTotal(valorTotal);
        pedido.setValorFrete(valorFrete);
        pedido.setData(LocalDate.now());

        PedidoAssinatura pedidoAssinatura = (PedidoAssinatura) pedido;

        pedidoAssinatura.setQuantidadeComprasRealizadas(0);
        pedidoAssinatura.setQuantidadePagamentosAntecipados(0);

        Assinatura assinatura = new Assinatura();
        assinatura.setPedidoAssinatura(pedidoAssinatura);
        assinatura.setProximaDataRenovacao(LocalDate.now().plusMonths(1));

        assinaturaRepository.save(assinatura);

        return pedidoAssinaturaRepository.save(pedidoAssinatura);
    }



    @Transactional
    @Override
    public Double realizarPagamento(Pedido pedido, List<PedidoItem> itens, PagamentoDTO pagamento) {
        Double valorPagamento = pagamento.getValorPagamento();
        Double desconto = 0.0;

        int quantidadePagamentosAntecipados = pagamento.getQuantidadePagamentosAntecipados();

        if(quantidadePagamentosAntecipados >= 3){
            desconto = descontoRepository.sumAllDescontosByPedido(pedido.getId());
        }

        Double valorTotalAPagar = pedido.getValorFrete() + pedido.getValorTotal() - desconto;

        PedidoAssinatura pedidoAssinatura = (PedidoAssinatura) pedido;

        if(valorTotalAPagar * quantidadePagamentosAntecipados > valorPagamento){
            throw new PagamentoExCustom(PagamentoEnumEx.VALOR_INVALIDO);
        }
        valorPagamento -= valorTotalAPagar;

        pedidoAssinatura.setStatusPedido(StatusPedido.FINALIZADO);
        pedidoAssinatura.setQuantidadePagamentosAntecipados(quantidadePagamentosAntecipados);
        for(PedidoItem item : itens) {
            item.setStatusPedido(StatusPedidoItem.FINALIZADO);
        }
        pedidoItemRepository.saveAll(itens);
        pedidoAssinaturaRepository.save(pedidoAssinatura);

        pagamentoService.repassarPagamento(pedidoAssinatura, itens);

        return valorPagamento;
    }
}

