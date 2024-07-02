package com.ufrn.imd.ecommerce.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO {
    private double valorTotal;
    private List<PedidoItemDTO> items;

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<PedidoItemDTO> getItems() {
        return items;
    }

    public void setItems(List<PedidoItemDTO> items) {
        this.items = items;
    }
}
