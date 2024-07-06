package com.ufrn.imd.ecommerce.services.interfaces;

import com.ufrn.imd.ecommerce.error.enunsEx.NotificacaoEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.NotificaoExCustom;
import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.NotificacaoRepository;
import com.ufrn.imd.ecommerce.services.ClienteService;
import com.ufrn.imd.ecommerce.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public abstract class NotificacaoSevice {

    private final NotificacaoRepository notificacaoRepository;
    private final EmailService emailService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ClienteService clienteService;

    public NotificacaoSevice(NotificacaoRepository notificacaoRepository, EmailService emailService) {
        this.notificacaoRepository = notificacaoRepository;
        this.emailService = emailService;
    }

    public void notificaUsuarios(Produto produto, int quantidade, Estoque estoque) {
        List<Notificacao> notificacoesASeremEnviadas = notificacaoRepository.findByProdutoAndQuantidade(produto.getAnuncio().getId(), quantidade);

        for (Notificacao notificacao : notificacoesASeremEnviadas) {
            boolean preCompraDeuCerto = this.usarCredito(notificacao.getUsuario(), produto, estoque);

            if(preCompraDeuCerto){
                removeNotificacao(notificacao);
            }

            Usuario usuario = clienteService.findUsuario(notificacao.getUsuario().getId());

            Email email = this.formatarEmail(preCompraDeuCerto, produto, usuario);

            this.enviarEmail(notificacao.getUsuario().getEmail(), email.getAssunto(), email.getConteudo());
        }
    }

    public void removeNotificacao(Notificacao notificacao) {
        notificacaoRepository.deleteById(notificacao.getId());
    }

    public Notificacao createNotificacao(Usuario usuario, Produto produto) throws NotificaoExCustom{
        if (!notificacaoRepository.findByUsuarioAndAnuncio(usuario, produto.getAnuncio()).isPresent()) {
            Notificacao notificacao = new Notificacao();
            notificacao.setUsuario(usuario);
            notificacao.setAnuncio(produto.getAnuncio());
            notificacao.setHoraDaSolicitacao(LocalDateTime.now());

            return notificacaoRepository.save(notificacao);
        }
        throw new NotificaoExCustom(NotificacaoEnumEx.NOTIFICAO_JA_CADASTRADA);
    }

    public abstract boolean usarCredito(Usuario usuario, Produto produto, Estoque estoque);

    public abstract Email formatarEmail(boolean preCompraDeuCreto, Produto produto, Usuario usuario);

    public void enviarEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
