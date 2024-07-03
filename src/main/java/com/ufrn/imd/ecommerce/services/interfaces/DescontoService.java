package com.ufrn.imd.ecommerce.services.interfaces;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface DescontoService {
    public void calcularDesconto(Map<Anunciante, Double> valorPorAnunciante, Pedido pedido);
}
