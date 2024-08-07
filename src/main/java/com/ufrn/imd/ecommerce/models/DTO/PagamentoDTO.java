package com.ufrn.imd.ecommerce.models.DTO;

public class PagamentoDTO {
    private Long idUsuario;
    private Long idPedido;
    private Double valorPagamento;
    private int quantidadePagamentosAntecipados;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(Double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public int getQuantidadePagamentosAntecipados() {
        return quantidadePagamentosAntecipados;
    }

    public void setQuantidadePagamentosAntecipados(int quantidadePagamentosAntecipados) {
        this.quantidadePagamentosAntecipados = quantidadePagamentosAntecipados;
    }
}
