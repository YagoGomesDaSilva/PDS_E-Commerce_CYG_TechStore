package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class PedidoAssinatura extends Pedido{

    private int quantidadePagamentosAntecipados;

    private int quantidadeComprasRealizadas;

    public int getQuantidadePagamentosAntecipados() {
        return quantidadePagamentosAntecipados;
    }

    public void setQuantidadePagamentosAntecipados(int quantidadePagamentosAntecipados) {
        this.quantidadePagamentosAntecipados = quantidadePagamentosAntecipados;
    }

    public int getQuantidadeComprasRealizadas() {
        return quantidadeComprasRealizadas;
    }

    public void setQuantidadeComprasRealizadas(int quantidadeComprasRealizadas) {
        this.quantidadeComprasRealizadas = quantidadeComprasRealizadas;
    }
}
