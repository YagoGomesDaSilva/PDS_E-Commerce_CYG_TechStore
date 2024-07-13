package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Desconto;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import com.ufrn.imd.ecommerce.repositories.AnuncianteRepository;
import com.ufrn.imd.ecommerce.repositories.DescontoRepository;
import com.ufrn.imd.ecommerce.services.interfaces.PagamentoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoComum implements PagamentoService {
    private final DescontoRepository descontoRepository;
    private final AnuncianteRepository anuncianteRepository;

    public PagamentoComum(DescontoRepository descontoRepository, AnuncianteRepository anuncianteRepository) {
        this.descontoRepository = descontoRepository;
        this.anuncianteRepository = anuncianteRepository;
    }

    @Override
    public void repassarPagamento(Pedido pedido, List<PedidoItem> itens) {
        Anunciante loja = new Anunciante();
        for (PedidoItem item : itens) {
            loja = item.getProduto().getAnunciante();
            loja.setCredito(loja.getCredito() + (item.getProduto().getValorTotal() * item.getQuantidade()));
        }
        Optional<Desconto> desconto = descontoRepository.findByPedidoAndAnunciante(pedido, loja);
        if(desconto.isPresent()){
            loja.setCredito(loja.getCredito() - desconto.get().getValorDesconto());
        }
        anuncianteRepository.save(loja);
    }
}
