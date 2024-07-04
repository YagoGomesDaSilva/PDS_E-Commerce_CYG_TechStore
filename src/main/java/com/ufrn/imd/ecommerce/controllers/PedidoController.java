package com.ufrn.imd.ecommerce.controllers;


import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.error.exceptions.EstoqueExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.PedidoExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.RegraNegocioException;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.DTO.PagamentoDTO;
import com.ufrn.imd.ecommerce.models.DTO.PedidoDTO;
import com.ufrn.imd.ecommerce.models.DTO.PedidoResponseDTO;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.services.AnuncianteService;
import com.ufrn.imd.ecommerce.services.PedidoItemService;
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
    @Autowired
    private AnuncianteService anuncianteService;
    @Autowired
    private PedidoItemService pedidoItemService;

    @PostMapping("/{idUsuario}")
    public PedidoResponseDTO realizarPedido(@PathVariable Long idUsuario){
        try {
            Pedido pedido = pedidoService.findPedidoByUsuario(idUsuario);
            List<PedidoItem> itens = pedidoItemService.findAllItemsByStatus(pedido.getUsuario().getId(), StatusPedidoItem.CARRINHO);
            pedido = pedidoService.realizarPedido(itens, pedido);

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
                Usuario usuario = usuarioService.findUsuario(pagamentoDTO.getIdUsuario());
                usuarioService.addCredito(usuario, credito);
            }

            return credito;
        } catch (PedidoExCustom err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
        }
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Pedido RealizarPedido(@RequestBody PedidoDTO dto, HttpServletRequest request)  {
//        try {
//            Anunciante usuario = anuncianteService.findUsuarioByToken(request);
//            Pedido pedido = pedidoService.createPedido(dto, usuario);
//            return pedido;
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<Pedido> findAll() {
//        try {
//            return pedidoService.findPedidos();
//        } catch (RegraNegocioException err) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Optional<Pedido> findFullById(@RequestParam Long id) {
//        try {
//            return pedidoService.findFullPedido(id);
//        } catch (PedidoExCustom err) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//    }

}
