package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Desconto;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import com.ufrn.imd.ecommerce.repositories.AnuncianteRepository;
import com.ufrn.imd.ecommerce.repositories.DescontoRepository;
import com.ufrn.imd.ecommerce.services.interfaces.PagamentoService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PagamentoParaAnunciante implements PagamentoService {
    private final DescontoRepository descontoRepository;
    private final AnuncianteRepository anuncianteRepository;

    public PagamentoParaAnunciante(DescontoRepository descontoRepository, AnuncianteRepository anuncianteRepository) {
        this.descontoRepository = descontoRepository;
        this.anuncianteRepository = anuncianteRepository;
    }

    @Override
    public void repassarPagamento(Pedido pedido, List<PedidoItem> itens) {
        Set<Anunciante> anunciantes = new HashSet<>();

        for (PedidoItem item : itens) {
            Anunciante anunciante = item.getProduto().getAnunciante();
            anunciante.setCredito(anunciante.getCredito() + (item.getProduto().getValorTotal() * item.getQuantidade()));
            anunciantes.add(anunciante);
        }

        for(Anunciante anunciante : anunciantes) {
            Optional<Desconto> desconto = descontoRepository.findByPedidoAndAnunciante(pedido, anunciante);
            if(desconto.isPresent()){
                anunciante.setCredito(anunciante.getCredito() - desconto.get().getValorDesconto());
            }
            anuncianteRepository.save(anunciante);
        }
    }
}
