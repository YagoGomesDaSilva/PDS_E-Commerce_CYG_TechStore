package com.ufrn.imd.ecommerce.controllers;


import com.ufrn.imd.ecommerce.error.exceptions.PedidoExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.RegraNegocioException;
import com.ufrn.imd.ecommerce.models.DTO.PedidoDTO;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.Cliente;
import com.ufrn.imd.ecommerce.services.AnuncianteService;
import com.ufrn.imd.ecommerce.services.PedidoService;
import com.ufrn.imd.ecommerce.services.ClienteService;
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
    private ClienteService clienteService;
    @Autowired
    private AnuncianteService anuncianteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido RealizarPedido(@RequestBody PedidoDTO dto, HttpServletRequest request)  {
        try {
            Anunciante usuario = anuncianteService.findUsuarioByToken(request);
            Pedido pedido = pedidoService.createPedido(dto, usuario);
            return pedido;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Pedido> findAll() {
        try {
            return pedidoService.findPedidos();
        } catch (RegraNegocioException err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Pedido> findFullById(@RequestParam Long id) {
        try {
            return pedidoService.findFullPedido(id);
        } catch (PedidoExCustom err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
