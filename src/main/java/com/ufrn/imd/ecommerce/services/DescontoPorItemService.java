package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.Desconto;
import com.ufrn.imd.ecommerce.repositories.DescontoRepository;
import com.ufrn.imd.ecommerce.repositories.EnderecoRepository;
import com.ufrn.imd.ecommerce.services.interfaces.DescontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DescontoPorItemService implements DescontoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private DescontoRepository descontoRepository;


    @Override
    public void calcularDesconto(Map<Anunciante, Double> valorPorAnunciante, Pedido pedido) {
        for(Map.Entry<Anunciante, Double> entry : valorPorAnunciante.entrySet()) {
            Desconto desconto = new Desconto();
            Anunciante anunciante = entry.getKey();
            Double valor = entry.getValue();
            String estado = enderecoRepository.findByUsuario(pedido.getUsuario()).getEstado();
            Double valorMinimo;
            Double frete;

            if (estado.equals("RN") || estado.equals("PB") || estado.equals("PE") || estado.equals("AL")
                    || estado.equals("CE") || estado.equals("MA") || estado.equals("PI") || estado.equals("BA") || estado.equals("SE")) {
                valorMinimo = 200.00;
                frete = 30.00;
            } else if (estado.equals("RS") || estado.equals("SC") || estado.equals("PR")) {
                valorMinimo = 400.00;
                frete = 60.00;
            } else if (estado.equals("SP") || estado.equals("RJ") || estado.equals("MG") || estado.equals("ES")) {
                valorMinimo = 350.00;
                frete = 50.00;
            } else if (estado.equals("DF") || estado.equals("GO") || estado.equals("MT") || estado.equals("MS")) {
                valorMinimo = 300.00;
                frete = 40.00;
            } else if (estado.equals("AM") || estado.equals("RR") || estado.equals("AP") || estado.equals("PA")
                    || estado.equals("TO") || estado.equals("RO") || estado.equals("AC")) {
                valorMinimo = 300.00;
                frete = 35.00;
            } else {
                valorMinimo = 800.00;
                frete = 70.00;
            }

            if(valor >= valorMinimo){
                desconto.setValorDesconto(frete);
            }
            if (desconto.getValorDesconto() != 0.0){
                desconto.setAnunciante(anunciante);
                desconto.setPedido(pedido);
                descontoRepository.save(desconto);
            }
        }
    }
}
