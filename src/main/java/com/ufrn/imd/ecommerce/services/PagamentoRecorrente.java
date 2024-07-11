package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.AnuncianteRepository;
import com.ufrn.imd.ecommerce.repositories.DescontoRepository;
import com.ufrn.imd.ecommerce.services.interfaces.PagamentoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoRecorrente implements PagamentoService {
    private final DescontoRepository descontoRepository;
    private final AnuncianteRepository anuncianteRepository;

    public PagamentoRecorrente(DescontoRepository descontoRepository, AnuncianteRepository anuncianteRepository) {
        this.descontoRepository = descontoRepository;
        this.anuncianteRepository = anuncianteRepository;
    }

    @Override
    public void repassarPagamento(Pedido pedido, List<PedidoItem> itens) {
        Anunciante loja = new Anunciante();

        PedidoAssinatura pedidoAssinatura = (PedidoAssinatura) pedido;
        Double valorTotal = 0.0;


        for (PedidoItem item : itens) {
            loja = item.getProduto().getAnunciante();
            valorTotal += (item.getProduto().getValorTotal() * item.getQuantidade());
        }

        loja.setCredito(loja.getCredito() + (valorTotal * pedidoAssinatura.getQuantidadePagamentosAntecipados()));

        if(pedidoAssinatura.getQuantidadePagamentosAntecipados() >= 3){
            Optional<Desconto> desconto = descontoRepository.findByPedidoAndAnunciante(pedido, loja);
            if(desconto.isPresent()){
                loja.setCredito(loja.getCredito() - desconto.get().getValorDesconto());
            }
        }

        anuncianteRepository.save(loja);
    }
}
