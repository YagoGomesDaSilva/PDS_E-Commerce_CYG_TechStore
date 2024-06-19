package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.repositories.ProdutoFavoritoRepository;

public class NotificacaoPreCompraService extends NotificacaoSevice{
    public NotificacaoPreCompraService(ProdutoFavoritoRepository produtoFavoritoRepository, EmailService emailService) {
        super(produtoFavoritoRepository, emailService);
    }

    @Override
    public void usarCredito() {

    }

    @Override
    public void formatarEmail() {

    }
}
