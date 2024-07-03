package com.ufrn.imd.ecommerce.models.DTO;

import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;

import java.util.ArrayList;
import java.util.List;

public class ItemPorAnuncianteDTO {
    private Long idAnunciante;

    private List<PedidoItem> itens;

    private Double desconto;

    public ItemPorAnuncianteDTO(){
        this.itens = new ArrayList<>();
    }

    public Long getIdAnunciante() {
        return idAnunciante;
    }

    public void setIdAnunciante(Long idAnunciante) {
        this.idAnunciante = idAnunciante;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public void addItem(PedidoItem pedidoItem) {
        this.itens.add(pedidoItem);
    }
}
