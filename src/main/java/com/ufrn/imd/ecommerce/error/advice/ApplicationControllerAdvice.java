package com.ufrn.imd.ecommerce.error.advice;

import com.ufrn.imd.ecommerce.error.exceptions.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //trata os exceptionHandlers --> trata erros quando eles acontecem
public class ApplicationControllerAdvice {

    /*
     * toda vez que api lançar essa exceção, cairá aqui!
     * precisa dizer qual status será retornado - por padrão - bad request - 400
     */
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
}
