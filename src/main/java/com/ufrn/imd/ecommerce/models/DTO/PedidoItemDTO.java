package com.ufrn.imd.ecommerce.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItemDTO {
    private Long produto;
    private Long quantidade;
}
