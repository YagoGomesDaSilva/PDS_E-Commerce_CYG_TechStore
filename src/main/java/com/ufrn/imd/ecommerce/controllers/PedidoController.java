package com.ufrn.imd.ecommerce.controllers;


import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.error.exceptions.EstoqueExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.PedidoExCustom;
import com.ufrn.imd.ecommerce.models.DTO.PagamentoDTO;
import com.ufrn.imd.ecommerce.models.DTO.PedidoResponseDTO;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.services.AnuncianteService;
import com.ufrn.imd.ecommerce.services.PedidoItemService;
import com.ufrn.imd.ecommerce.services.PedidoService;
import com.ufrn.imd.ecommerce.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    @Qualifier("pedidoAssinaturaService")
    private PedidoService pedidoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private AnuncianteService anuncianteService;
    @Autowired
    private PedidoItemService pedidoItemService;

    @PostMapping("/{idUsuario}")
    public Pedido realizarPedido(@PathVariable Long idUsuario){
        try {
            Pedido pedido = pedidoService.findPedidoByUsuario(idUsuario, StatusPedido.EM_ANDAMENTO);
            List<PedidoItem> itens = pedidoItemService.findAllItemsByStatus(pedido.getUsuario().getId(), StatusPedidoItem.CARRINHO);
            return pedidoService.realizarPedido(itens, pedido);
        } catch (EstoqueExCustom | PedidoExCustom err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
        }
    }

    @GetMapping("/{idUsuario}")
    public PedidoResponseDTO getPedidoByUsuario(@PathVariable Long idUsuario){
        try {
            Pedido pedido = pedidoService.findPedidoByUsuario(idUsuario, StatusPedido.AGUARDANDO_PAGAMENTO);
            List<PedidoItem> itens = pedidoItemService.findAllItemsByStatus(pedido.getUsuario().getId(), StatusPedidoItem.AGUARDANDO_PAGAMENTO);

            return pedidoService.prepararResposta(itens, pedido);
        } catch (EstoqueExCustom | PedidoExCustom err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
        }
    }

    @PostMapping("/pagamento")
    public Double realizarPagamento(@RequestBody PagamentoDTO pagamentoDTO){
        try {
            Pedido pedido = pedidoService.findPedido(pagamentoDTO.getIdPedido());
            List<PedidoItem> pedidoItems = pedidoItemService.findAllItemsByStatus(pagamentoDTO.getIdUsuario(), StatusPedidoItem.AGUARDANDO_PAGAMENTO);

            Double credito =  pedidoService.realizarPagamento(pedido, pedidoItems, pagamentoDTO.getValorPagamento());

            if(credito > 0.0){
                Usuario usuario = clienteService.findUsuario(pagamentoDTO.getIdUsuario());
                clienteService.addCredito(usuario, credito);
            }

            return credito;
        } catch (PedidoExCustom err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
        }
    }
}
