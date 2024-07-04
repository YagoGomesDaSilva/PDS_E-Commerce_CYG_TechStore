package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Desconto;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.repositories.DescontoRepository;
import com.ufrn.imd.ecommerce.services.interfaces.DescontoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DescontoPorAnuncianteService implements DescontoService {
    private final DescontoRepository descontoRepository;

    public DescontoPorAnuncianteService(DescontoRepository descontoRepository) {
        this.descontoRepository = descontoRepository;
    }

    @Override
    public void calcularDesconto(Map<Anunciante, Double> valorPorAnunciante, Pedido pedido) {
        for(Map.Entry<Anunciante, Double> entry : valorPorAnunciante.entrySet()){
            Desconto desconto = new Desconto();
            Anunciante anunciante = entry.getKey();
            Double valor = entry.getValue();
            if(valor >= 400){
                desconto.setValorDesconto(valor * 0.1);
            } else if (valor >= 200) {
                desconto.setValorDesconto(valor * 0.05);
            }

            if (desconto.getValorDesconto() != 0.0){
                desconto.setAnunciante(anunciante);
                desconto.setPedido(pedido);
                descontoRepository.save(desconto);
            }
        }
    }
}
