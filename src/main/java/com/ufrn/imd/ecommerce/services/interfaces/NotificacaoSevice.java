package com.ufrn.imd.ecommerce.services.interfaces;

import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.models.entidades.ProdutoFavorito;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.repositories.ProdutoFavoritoRepository;
import com.ufrn.imd.ecommerce.services.EmailService;

import java.util.List;

public abstract class NotificacaoSevice {

    private final ProdutoFavoritoRepository produtoFavoritoRepository;
    private final EmailService emailService; // Supondo que exista um serviço para envio de e-mails

    public NotificacaoSevice(ProdutoFavoritoRepository produtoFavoritoRepository, EmailService emailService) {
        this.produtoFavoritoRepository = produtoFavoritoRepository;
        this.emailService = emailService;
    }

    public void notifyUsers(Produto produto) {
        List<ProdutoFavorito> favoritos = produtoFavoritoRepository.findByProduto(produto);

        for (ProdutoFavorito favorito : favoritos) {
            Usuario user = favorito.getUsuario();
            emailService.sendEmail(user.getEmail(), "Produto disponível", "O produto " + produto.getNome() + " está de volta ao estoque.");
        }
    }

    public void createNotificacao(){

    }

    public void enviarEmail(){

    }

    public abstract void usarCredito();

    public abstract void formatarEmail();
}
