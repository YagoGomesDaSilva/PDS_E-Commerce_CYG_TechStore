package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.EnderecoEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.EnderecoExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Endereco;
import com.ufrn.imd.ecommerce.repositories.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    public final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public void createEndereco(Endereco endereco){
        enderecoRepository.save(endereco);
    }

    public void updateEndereco(Endereco endereco){
        if (enderecoRepository.findById(endereco.getId()).isPresent()){
            //Implementar update Endereco
        } else{
            throw new EnderecoExCustom(EnderecoEnumEx.ENDERECO_NAO_ENCONTRADO);
        }
    }

    public void deleteEndereco(Endereco endereco){
        if (enderecoRepository.findById(endereco.getId()).isPresent()){
            enderecoRepository.delete(endereco);
        } else{
            throw new EnderecoExCustom(EnderecoEnumEx.ENDERECO_NAO_ENCONTRADO);
        }
    }

}
