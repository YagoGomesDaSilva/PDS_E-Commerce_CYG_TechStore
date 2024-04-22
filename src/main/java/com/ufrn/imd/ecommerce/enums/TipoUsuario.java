package com.ufrn.imd.ecommerce.enums;

public enum TipoUsuario {
    ADMIN("admin"),
    ANUNCIANTE("anunciante"),
    COMUM("comum");

    private String role;

    TipoUsuario(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
