package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.services.interfaces.DescontoService;

import java.util.Map;

public class DescontoAssinaturaService implements DescontoService {
    @Override
    public void calcularDesconto(Map<Anunciante, Double> valorPorAnunciante, Pedido pedido) {
    }
}
