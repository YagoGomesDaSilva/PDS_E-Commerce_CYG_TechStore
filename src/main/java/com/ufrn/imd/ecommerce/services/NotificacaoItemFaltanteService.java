package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.models.entidades.Email;
import com.ufrn.imd.ecommerce.models.entidades.Estoque;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import com.ufrn.imd.ecommerce.repositories.NotificacaoRepository;
import com.ufrn.imd.ecommerce.services.interfaces.NotificacaoSevice;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoItemFaltanteService extends NotificacaoSevice {
    public NotificacaoItemFaltanteService(NotificacaoRepository notificacaoRepository, EmailService emailService) {
        super(notificacaoRepository, emailService);
    }

    @Override
    public boolean usarCredito(Usuario usuario, Produto produto, Estoque estoque) {

        return false;
    }

    @Override
    public Email formatarEmail(boolean preCompraDeuCreto, Produto produto, Usuario usuario) {

        Email email = new Email();
        email.setAssunto("CORRA!!! Seu produto está com estoque disponível!!!");
        email.setConteudo("Olá " + usuario.getNome() + "! \n" +
                "\n" +
                "O produto " + produto.getNome() + " acabou de chegar em nosso estoque! Adiquira logo antes que se esgote novamente");

        return email;
    }
}
