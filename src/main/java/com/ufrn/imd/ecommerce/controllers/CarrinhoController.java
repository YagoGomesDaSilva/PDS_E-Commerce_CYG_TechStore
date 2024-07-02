package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.error.exceptions.AnuncioExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Anuncio;
import com.ufrn.imd.ecommerce.models.entidades.Pedido;
import com.ufrn.imd.ecommerce.models.entidades.PedidoItem;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.services.AnuncioService;
import com.ufrn.imd.ecommerce.services.PedidoItemService;
import com.ufrn.imd.ecommerce.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CarrinhoController {

    private final AnuncioService anuncioService;
    private final PedidoItemService pedidoItemService;
    private final UsuarioService usuarioService;

    public CarrinhoController(AnuncioService anuncioService, PedidoItemService pedidoItemService, UsuarioService usuarioService) {
        this.anuncioService = anuncioService;
        this.pedidoItemService = pedidoItemService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/{idUsuario}/{idAnuncio}")
    public PedidoItem addItemToCart(@PathVariable Long idUsuario, @PathVariable Long idAnuncio){
        try {
            Anuncio anuncio = anuncioService.findAnuncio(idAnuncio);
            Usuario usuario = usuarioService.findUsuario(idUsuario);
            return pedidoItemService.addItemToCart(usuario, anuncio);
        } catch (AnuncioExCustom e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{idUsuario}")
    public List<PedidoItem> findAllItems(@PathVariable Long idUsuario){
        return pedidoItemService.findAllItemsInCart(idUsuario);
    }

    @DeleteMapping ("/{idPedidoItem}")
    public String removeItemToCart(@PathVariable Long idPedidoItem){
        try {
            pedidoItemService.removeItemToCart(idPedidoItem);
            return "Item: " + idPedidoItem + " removido com sucesso!";
        } catch (AnuncioExCustom e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
