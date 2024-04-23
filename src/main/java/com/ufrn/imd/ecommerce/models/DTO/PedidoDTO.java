package com.ufrn.imd.ecommerce.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long compradorId;
    private BigDecimal valorTotal;
    private List<PedidoItemDTO> items;
}
