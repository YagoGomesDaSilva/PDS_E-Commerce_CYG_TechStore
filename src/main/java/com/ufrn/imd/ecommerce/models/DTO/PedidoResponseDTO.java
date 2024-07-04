package com.ufrn.imd.ecommerce.models.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoResponseDTO {
    private List<ItemPorAnuncianteDTO> itensAnunciante;
    private Double valorTotal;
    private Double valorTotalComFrete;
    private Double valorTotalComDesconto;
    private Double valorFrete;

    public PedidoResponseDTO() {
        this.itensAnunciante = new ArrayList<>();
        this.valorTotalComDesconto = 0.0;
        this.valorTotal = 0.0;
    }

    public List<ItemPorAnuncianteDTO> getItensAnunciante() {
        return itensAnunciante;
    }

    public void setItensAnunciante(List<ItemPorAnuncianteDTO> itensAnunciante) {
        this.itensAnunciante = itensAnunciante;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorTotalComDesconto() {
        return valorTotalComDesconto;
    }

    public void setValorTotalComDesconto(Double valorTotalComDesconto) {
        this.valorTotalComDesconto = valorTotalComDesconto;
    }

    public Double getValorTotalComFrete() {
        return valorTotalComFrete;
    }

    public void setValorTotalComFrete(Double valorTotalComFrete) {
        this.valorTotalComFrete = valorTotalComFrete;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Optional<ItemPorAnuncianteDTO> find(Long idAnunciante){
        for(ItemPorAnuncianteDTO item : this.itensAnunciante) {
            if(idAnunciante == item.getIdAnunciante()){
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}
