package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.enums.StatusPedido;
import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.*;
import com.ufrn.imd.ecommerce.services.interfaces.NotificacaoSevice;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoPreCompraService extends NotificacaoSevice {
    private final PedidoService pedidoService;
    private final EnderecoRepository enderecoRepository;
    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstoqueRepository estoqueRepository;

    public NotificacaoPreCompraService(NotificacaoRepository notificacaoRepository, EmailService emailService, PedidoService pedidoService, EnderecoRepository enderecoRepository, PedidoRepository pedidoRepository, PedidoItemRepository pedidoItemRepository, UsuarioRepository usuarioRepository, UsuarioRepository usuarioRepository1, EstoqueRepository estoqueRepository) {
        super(notificacaoRepository, emailService);
        this.pedidoService = pedidoService;
        this.enderecoRepository = enderecoRepository;
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.usuarioRepository = usuarioRepository1;
        this.estoqueRepository = estoqueRepository;
    }

    @Override
    public boolean usarCredito(Usuario usuario, Produto produto, Estoque estoque) {
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
        email.setAssunto("Infelizmente não foi possível realizar sua pré-compra!");
        email.setConteudo("Olá " + usuario.getNome() + "! \n" +
                "\n" +
                "O produto " + produto.getNome() + " que você solicitou voltou para o estoque, porém você não possui crédito em conta suficiente para realizar a compra! \n" +
                "Entre em nosso site e realize a compra enquanto o produto está disponível! Não perca essa oportunidade!");

        return email;
    }

    private Email preCompraRealizada(Produto produto, Usuario usuario) {
        Email email = new Email();
        email.setAssunto("Sua pré-compra foi realizada com sucesso!");
        email.setConteudo("Olá " + usuario.getNome() + "! \n" +
                "\n" +
                "O produto " + produto.getNome() + " que você solicitou voltou para o estoque e já realizamos a compra para você utilizando seu crédito em conta!");

        return email;
    }
}
