package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.MovimentacaoEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.MovimentacaoExCustom;
import com.ufrn.imd.ecommerce.models.entidades.MovimentacaoEstoque;
import com.ufrn.imd.ecommerce.repositories.MovimentacaoEstoqueRepository;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoService {
    public final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public MovimentacaoService(MovimentacaoEstoqueRepository movimentacaoEstoqueRepository) {
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }

    public void createMovimentacao(MovimentacaoEstoque movimentacaoEstoque){
        movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }

    public void updateMovimentacao(MovimentacaoEstoque movimentacaoEstoque){
        if (movimentacaoEstoqueRepository.findById(movimentacaoEstoque.getId()).isPresent()){
            //Implementar update Movimentacao
        } else{
            throw new MovimentacaoExCustom(MovimentacaoEnumEx.MOVIMENTACAO_NAO_ENCONTRADA);
        }
    }

    public void deleteMovimentacao(MovimentacaoEstoque movimentacaoEstoque){
        if (movimentacaoEstoqueRepository.findById(movimentacaoEstoque.getId()).isPresent()){
            movimentacaoEstoqueRepository.delete(movimentacaoEstoque);
        } else{
            throw new MovimentacaoExCustom(MovimentacaoEnumEx.MOVIMENTACAO_NAO_ENCONTRADA);
        }
    }
}
