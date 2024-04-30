package com.ufrn.imd.ecommerce.controllers;


import com.ufrn.imd.ecommerce.models.DTO.PedidoDTO;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido save( @RequestBody PedidoDTO dto )  {
        Pedido pedido = pedidoService.createPedido(dto);
        return pedido;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Pedido> findAll() {
        return pedidoService.findPedidos();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Optional<Pedido> findFullById(@RequestParam Long id) {
        return pedidoService.findFullPedido(id);
    }

}
