package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.CartaoEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.CartaoExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Cartao;
import com.ufrn.imd.ecommerce.repositories.CartaoRepository;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public void createCartao(Cartao cartao) {
        cartaoRepository.save(cartao);
    }
    public void updateCartao(Cartao cartao) {
        if(cartaoRepository.findById(cartao.getId()).isPresent()){
            //Implementar update Cartão
        } else {
            throw new CartaoExCustom(CartaoEnumEx.CARTAO_NAO_ENCONTRADO);
        }
    }
    public void deleteCartao(Cartao cartao) {
        if(cartaoRepository.findById(cartao.getId()).isPresent()){
            cartaoRepository.delete(cartao);
        } else {
            throw new CartaoExCustom(CartaoEnumEx.CARTAO_NAO_ENCONTRADO);
        }
    }
}
