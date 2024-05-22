package com.ufrn.imd.ecommerce.services;

import com.ufrn.imd.ecommerce.error.enunsEx.CategoriaEnumEx;
import com.ufrn.imd.ecommerce.error.exceptions.CategoriaExCustom;
import com.ufrn.imd.ecommerce.models.entidades.Categoria;
import com.ufrn.imd.ecommerce.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    public final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public void createCategoria(Categoria categoria){
        categoriaRepository.save(categoria);
    }

    public void updateCategoria(Categoria categoria){
        if(categoriaRepository.findById(categoria.getId()).isPresent()){
            //Implementar update Categoria
        } else {
            throw new CategoriaExCustom(CategoriaEnumEx.CATEGORIA_NAO_ENCONTRADA);
        }
    }

    public void deleteCategoria(Categoria categoria){
        if(categoriaRepository.findById(categoria.getId()).isPresent()){
            categoriaRepository.delete(categoria);
        } else {
            throw new CategoriaExCustom(CategoriaEnumEx.CATEGORIA_NAO_ENCONTRADA);
        }
    }

}
