package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.services.interfaces.DescontoService;

import java.util.Map;

public class DescontoPorItemService implements DescontoService {
    @Override
    public void calcularDesconto(Map<Anunciante, Double> valorPorAnunciante, Pedido pedido) {
        Desconto desconto = new Desconto();
        String estado = enderecoRepository.findByUsuario(pedido.getUsuario()).getEstado();
        Double valorMinimo;
        if(estado.equals("RN") || estado.equals("PB") || estado.equals("PE") || estado.equals("AL")
                || estado.equals("CE") || estado.equals("MA") || estado.equals("PI") || estado.equals("BA") || estado.equals("SE") ){
            valorMinimo = 200.00;
        } else if (estado.equals("RS") || estado.equals("SC") || estado.equals("PR")){
            valorMinimo = 400.00;
        } else if (estado.equals("SP") || estado.equals("RJ") || estado.equals("MG") || estado.equals("ES")){
            valorMinimo = 350.00;
        } else if (estado.equals("DF") || estado.equals("GO") || estado.equals("MT") || estado.equals("MS")) {
            valorMinimo = 300.00;
        } else if (estado.equals("AM") || estado.equals("RR") || estado.equals("AP") || estado.equals("PA")
                || estado.equals("TO") || estado.equals("RO") || estado.equals("AC")) {
            valorMinimo = 300.00;
        } else {
            valorMinimo = 800.00;
        }
    }
}
