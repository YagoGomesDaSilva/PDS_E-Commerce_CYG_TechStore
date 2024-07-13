package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.*;
import com.ufrn.imd.ecommerce.services.interfaces.NotificacaoSevice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticacaoAssinaturaService extends NotificacaoSevice {
    private final PedidoAssinaturaRepository pedidoAssinaturaRepository;
    private final EnderecoRepository enderecoRepository;
    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstoqueRepository estoqueRepository;

    public NoticacaoAssinaturaService(NotificacaoRepository notificacaoRepository, EmailService emailService, PedidoAssinaturaRepository pedidoAssinaturaRepository, EnderecoRepository enderecoRepository, @Qualifier("pedidoService") PedidoService pedidoService, PedidoRepository pedidoRepository, PedidoItemRepository pedidoItemRepository, UsuarioRepository usuarioRepository, EstoqueRepository estoqueRepository) {
        super(notificacaoRepository, emailService);
        this.pedidoAssinaturaRepository = pedidoAssinaturaRepository;
        this.enderecoRepository = enderecoRepository;
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.usuarioRepository = usuarioRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Override
    public boolean usarCredito(Usuario usuario, Produto produto, Estoque estoque) {
        List<PedidoItemAssinatura> itensDevolvidos = pedidoAssinaturaRepository.findItensDevolvidoByUsuario(usuario.getId());

        for(PedidoItemAssinatura item : itensDevolvidos){
            if(item.getProduto().getId() == produto.getId()){
                if(usuario.getCredito() >= produto.getValorTotal()){
                    Pedido pedido = new Pedido();
                    pedido.setUsuario(usuario);
                    pedido.setStatusPedido(StatusPedido.FINALIZADO);
                    pedido.setValorFrete(pedidoService.calcularFrete(enderecoRepository.findByUsuario(pedido.getUsuario()).getEstado()));
                    pedido.setValorTotal(produto.getValorTotal());
                    pedido = pedidoRepository.save(pedido);

                    PedidoItem pedidoItem = new PedidoItem();
                    pedidoItem.setProduto(produto);
                    pedidoItem.setQuantidade(1);
                    pedidoItem.setStatusPedido(StatusPedidoItem.FINALIZADO);
                    pedidoItem.setPedido(pedido);
                    pedidoItemRepository.save(pedidoItem);

                    usuario.setCredito(usuario.getCredito() - produto.getValorTotal());
                    usuarioRepository.save(usuario);

                    estoque.setQuantidade(estoque.getQuantidade() - 1);
                    estoqueRepository.save(estoque);

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Email formatarEmail(boolean preCompraDeuCreto, Produto produto, Usuario usuario) {
        Email email;
        if(preCompraDeuCreto){
            email = this.preCompraRealizada(produto, usuario);
        } else {
            email = this.preCompraNaoRealizada(produto, usuario);
        }

        return email;
    }

    private Email preCompraNaoRealizada(Produto produto, Usuario usuario) {
        Email email = new Email();
        email.setAssunto("O produto que estava faltando na sua compra recorrente retornou ao estoque!");
        email.setConteudo("Olá " + usuario.getNome() + "! \n" +
                "\n" +
                "O produto " + produto.getNome() + " que ficou faltando na sua compra recorrente voltou para o estoque, porém você não possui crédito em conta suficiente para realizar a compra! \n" +
                "Entre em nosso site e realize a compra enquanto o produto está disponível! Não perca essa oportunidade!");

        return email;
    }

    private Email preCompraRealizada(Produto produto, Usuario usuario) {
        Email email = new Email();
        email.setAssunto("O produto que estava faltando na sua compra recorrente retornou ao estoque!");
        email.setConteudo("Olá " + usuario.getNome() + "! \n" +
                "\n" +
                "O produto " + produto.getNome() + " retornou ao estoque e já realizamos a compra dele para você!");

        return email;
    }
}
