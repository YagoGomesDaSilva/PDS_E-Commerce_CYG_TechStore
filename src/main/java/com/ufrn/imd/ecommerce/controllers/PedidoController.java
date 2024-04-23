package com.ufrn.imd.ecommerce.controllers;


import com.ufrn.imd.ecommerce.models.DTO.PedidoDTO;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido save( @RequestBody PedidoDTO dto ) throws Exception {
        Pedido pedido = pedidoService.createPedido(dto);
        return pedido;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoDTO> findAll() {
        // to-do
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Pedido findById(@RequestParam Long id) {
        // to-do
    }

}
