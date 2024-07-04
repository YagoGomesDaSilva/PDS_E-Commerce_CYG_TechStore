package com.ufrn.imd.ecommerce.models.DTO;

public class CreditoDTO {
    private Long idUsuario;
    private Double credito;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }
}
