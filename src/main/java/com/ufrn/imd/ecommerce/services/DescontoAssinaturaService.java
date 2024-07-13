package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Desconto;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoAssinatura;
import com.ufrn.imd.ecommerce.repositories.DescontoRepository;
import com.ufrn.imd.ecommerce.services.interfaces.DescontoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DescontoAssinaturaService implements DescontoService {
    private final DescontoRepository descontoRepository;

    public DescontoAssinaturaService(DescontoRepository descontoRepository) {
        this.descontoRepository = descontoRepository;
    }

    @Override
    public void calcularDesconto(Map<Anunciante, Double> valorPorAnunciante, Pedido pedido) {
        for(Map.Entry<Anunciante, Double> entry : valorPorAnunciante.entrySet()) {
            Desconto desconto = new Desconto();
            Anunciante anunciante = entry.getKey();
            Double valor = entry.getValue();

            desconto.setValorDesconto(valor * 0.03);
            desconto.setPedido(pedido);
            descontoRepository.save(desconto);
        }
    }
}
