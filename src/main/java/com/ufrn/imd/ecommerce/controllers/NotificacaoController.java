package com.ufrn.imd.ecommerce.controllers;

import com.ufrn.imd.ecommerce.error.exceptions.NotificaoExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Anuncio;
import com.ufrn.imd.ecommerce.models.entidades.Notificacao;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.services.AnuncianteService;
import com.ufrn.imd.ecommerce.services.AnuncioService;
import com.ufrn.imd.ecommerce.services.ClienteService;
import com.ufrn.imd.ecommerce.services.NotificacaoPreCompraService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    private final AnuncioService anuncioService;
    private final ClienteService clienteService;
    private final NotificacaoPreCompraService notificacaoPreCompraService;

    public NotificacaoController(AnuncioService anuncioService, ClienteService clienteService, NotificacaoPreCompraService notificacaoPreCompraService) {
        this.anuncioService = anuncioService;
        this.clienteService = clienteService;
        this.notificacaoPreCompraService = notificacaoPreCompraService;
    }

    @PostMapping
    public Notificacao socilicitaNotificacao(@RequestParam Long idAnuncio, @RequestParam Long idUsuario){
        try {
            Anuncio anuncio = anuncioService.findAnuncio(idAnuncio);
            Usuario usuario = clienteService.findUsuario(idUsuario);

            return notificacaoPreCompraService.createNotificacao(usuario, anuncio.getProduto());
        } catch (NotificaoExCustom e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
