package com.ufrn.imd.ecommerce.models.entidades;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class PedidoAssinatura extends Pedido{

    private int quantidadePagamentosAntecipados;

    private int quantidadeComprasRealizadas;

    public PedidoAssinatura(Pedido pedido) {
        this.id = pedido.getId();
        this.pedidoItems = pedido.getPedidoItems();
        this.usuario = pedido.getUsuario();
        this.statusPedido = pedido.getStatusPedido();
        this.valorTotal = pedido.getValorTotal();
        this.valorFrete = pedido.getValorFrete();
        this.valorTotalItens = pedido.getValorTotalItens();
        this.data = pedido.getData();
        this.statusPedido = pedido.getStatusPedido();
    }

    public PedidoAssinatura() {

    }

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
