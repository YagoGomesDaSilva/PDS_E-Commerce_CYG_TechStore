package com.ufrn.imd.ecommerce.controllers;


import com.ufrn.imd.ecommerce.models.DTO.PedidoDTO;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.UsuarioConcreto;
import com.ufrn.imd.ecommerce.services.PedidoService;
import com.ufrn.imd.ecommerce.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido RealizarPedido(@RequestBody PedidoDTO dto, HttpServletRequest request)  {
        try {
            UsuarioConcreto usuario = usuarioService.findUsuarioByToken(request);
            Pedido pedido = pedidoService.createPedido(dto, usuario);
            return pedido;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Pedido> findAll() {
        return pedidoService.findPedidos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Pedido> findFullById(@RequestParam Long id) {
        return pedidoService.findFullPedido(id);
    }

}
