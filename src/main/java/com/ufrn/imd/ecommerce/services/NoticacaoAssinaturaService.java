package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.repositories.ProdutoFavoritoRepository;

public class NoticacaoAssinaturaService extends NotificacaoSevice{
    public NoticacaoAssinaturaService(ProdutoFavoritoRepository produtoFavoritoRepository, EmailService emailService) {
        super(produtoFavoritoRepository, emailService);
    }

    @Override
    public void usarCredito() {

    }

    @Override
    public void formatarEmail() {

    }
}
