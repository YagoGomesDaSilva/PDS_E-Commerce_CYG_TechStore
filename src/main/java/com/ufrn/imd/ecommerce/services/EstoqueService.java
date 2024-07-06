package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.EstoqueEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.ProdutoEnumEx;
import com.ufrn.imd.ecommerce.error.enunsEx.UsuarioEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.EstoqueExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.ProdutoExCustom;
import com.ufrn.imd.ecommerce.error.exceptions.UsuarioExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Anunciante;
import com.ufrn.imd.ecommerce.models.entidades.Estoque;
import com.ufrn.imd.ecommerce.models.entidades.Produto;
import com.ufrn.imd.ecommerce.repositories.AnuncianteRepository;
import com.ufrn.imd.ecommerce.repositories.EstoqueRepository;
import com.ufrn.imd.ecommerce.repositories.ProdutoRepository;
import com.ufrn.imd.ecommerce.services.interfaces.NotificacaoSevice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {


    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;
    private final AnuncianteRepository anuncianteRepository;
    private final NotificacaoSevice notificacaoSevice;

    public EstoqueService(EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository, AnuncianteRepository anuncianteRepository, @Qualifier("notificacaoPreCompraService") NotificacaoSevice notificacaoSevice) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
        this.anuncianteRepository = anuncianteRepository;
        this.notificacaoSevice = notificacaoSevice;
    }


    public Estoque updateEstoque(Anunciante anunciante, Produto produto, int quantidade) {
        List<Estoque> estoques = anunciante.getEstoques();

        Estoque estoque = verificaExistenciaProduto(estoques, produto.getId());
        if(estoque == null){
            throw new ProdutoExCustom(ProdutoEnumEx.PRODUTO_NAO_ENCONTRADO);
        }

        int novaQuantidade = estoque.getQuantidade() + quantidade;
        if (novaQuantidade < 0) {
            throw new EstoqueExCustom(EstoqueEnumEx.QUANTIDADE_INVALIDA);
        }
        estoque.setQuantidade(novaQuantidade);

        if(novaQuantidade > 0) {
            notificacaoSevice.notificaUsuarios(produto, novaQuantidade, estoque);
        }

        return estoqueRepository.save(estoque);
    }

    public Optional<Estoque> createEstoque(Estoque estoqueDTO, Long anuncianteId, Produto produto){

        Optional<Anunciante> anuncianteOpc = anuncianteRepository.findAnuncianteById(anuncianteId);
        Anunciante anunciante = anuncianteOpc.orElseThrow(() -> new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO));

        List<Estoque> estoques = anunciante.getEstoques();

        if(verificaExistenciaProduto(estoques, produto.getId()) != null){
            throw new EstoqueExCustom(EstoqueEnumEx.ESTOQUE_JA_EXISTENTE);
        }
        produtoRepository.save(produto);
        Estoque estoque = new Estoque();
        estoque.setAnunciante(anunciante);
        estoque.setProduto(produto);
        if(estoqueDTO.getQuantidade() <= 0) {
            throw new EstoqueExCustom(EstoqueEnumEx.QUANTIDADE_INVALIDA);
        }
        estoque.setQuantidade(estoqueDTO.getQuantidade());
        return Optional.of(estoqueRepository.save(estoque));
    }

    public Optional<Estoque> deleteEstoque(Long anuncianteId, Produto produto){
        Optional<Anunciante> anuncianteOpc = anuncianteRepository.findAnuncianteByIdWithDetails(anuncianteId);
        Anunciante anunciante = anuncianteOpc.orElseThrow(() -> new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO));

        List<Estoque> estoques = anunciante.getEstoques();

        Estoque estoque = verificaExistenciaProduto(estoques, produto.getId());

        if(estoque == null){
            throw new EstoqueExCustom(EstoqueEnumEx.ESTOQUE_NAO_ENCONTRADO);
        }

        produtoRepository.delete(produto);
        estoque.setAnunciante(null);
        estoqueRepository.save(estoque);
        estoqueRepository.delete(estoque);
        return Optional.of(estoque);
    }

    public Optional<Estoque> findEstoque(Long anuncianteId, Produto produto){
        Optional<Anunciante> anuncianteOpc = anuncianteRepository.findAnuncianteByIdWithDetails(anuncianteId);
        Anunciante anunciante = anuncianteOpc.orElseThrow(() -> new UsuarioExCustom(UsuarioEnumEx.USUARIO_NAO_ENCONTRADO));

        List<Estoque> estoques = anunciante.getEstoques();

        Estoque estoque = verificaExistenciaProduto(estoques, produto.getId());

        if(estoque == null){
            throw new EstoqueExCustom(EstoqueEnumEx.ESTOQUE_NAO_ENCONTRADO);
        }

        return Optional.of(estoque);
    }

    private Estoque verificaExistenciaProduto( List<Estoque> estoques, Long idProcurado) {
        for (Estoque estoque : estoques) {
            if (estoque.getProduto().getId().equals(idProcurado)) {
                return estoque;
            }
        }
        return null;
    }
}

