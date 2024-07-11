package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.enums.StatusPedidoItem;
import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.EstoqueExCustom;
import com.ufrn.imd.ecommerce.models.entidades.*;
import com.ufrn.imd.ecommerce.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final PedidoAssinaturaRepository pedidoAssinaturaRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final EstoqueRepository estoqueRepository;
    private final PedidoItemAssinaturaRepository pedidoItemAssinaturaRepository;
    private final UsuarioRepository usuarioRepository;

    public AssinaturaService(AssinaturaRepository assinaturaRepository, PedidoAssinaturaRepository pedidoAssinaturaRepository, PedidoItemRepository pedidoItemRepository, EstoqueRepository estoqueRepository, PedidoItemAssinaturaRepository pedidoItemAssinaturaRepository, UsuarioRepository usuarioRepository) {
        this.assinaturaRepository = assinaturaRepository;
        this.pedidoAssinaturaRepository = pedidoAssinaturaRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.estoqueRepository = estoqueRepository;
        this.pedidoItemAssinaturaRepository = pedidoItemAssinaturaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void renovarAssinaturas(){
        List<Assinatura> assinaturas = assinaturaRepository.findAllByProximaDataRenovacao(LocalDate.now());

        for(Assinatura assinatura : assinaturas) {
            PedidoAssinatura pedidoAssinatura = assinatura.getPedidoAssinatura();

            Usuario usuario = pedidoAssinatura.getUsuario();

            if(pedidoAssinatura.getQuantidadePagamentosAntecipados() < pedidoAssinatura.getQuantidadeComprasRealizadas()){
                List<PedidoItem> itens = pedidoItemRepository.findAllByPedido(pedidoAssinatura);

                for(PedidoItem item : itens){
                    PedidoItemAssinatura itemAssinatura = (PedidoItemAssinatura) item;
                    itemAssinatura.setDevolvido(false);

                    Optional<Estoque> estoque = estoqueRepository.findByProduto(itemAssinatura.getProduto().getId());
                    if(!estoque.isPresent()){
                        throw new EstoqueExCustom(EstoqueEnumEx.ESTOQUE_NAO_ENCONTRADO);
                    }
                    if(estoque.get().getQuantidade() < itemAssinatura.getQuantidade()){
                        usuario.setCredito(usuario.getCredito() + (itemAssinatura.getQuantidade() * itemAssinatura.getProduto().getValorTotal()));
                        usuarioRepository.save(usuario);

                        // enviar email informando que o item não estava disponivel em estoque e que o valor foi devolvido em crédito
                    }
                    estoque.get().setQuantidade(estoque.get().getQuantidade() - itemAssinatura.getQuantidade());
                    estoqueRepository.save(estoque.get());

                    itemAssinatura.setStatusPedido(StatusPedidoItem.FINALIZADO);
                    pedidoItemAssinaturaRepository.save(itemAssinatura);
                }

                pedidoAssinatura.setQuantidadeComprasRealizadas(pedidoAssinatura.getQuantidadeComprasRealizadas() + 1);
                pedidoAssinaturaRepository.save(pedidoAssinatura);
            } else {
                // enviar email informando que um novo pagamento precisa ser feito!
            }
        }
    }
}
