package com.dssd.grupo15.backend.dto.rest.request;

import java.math.BigDecimal;

public class SocioDTO {
    private String nombre;
    private String apellido;
    private BigDecimal aportes;

    public SocioDTO(String nombre, String apellido, BigDecimal aportes) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.aportes = aportes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public BigDecimal getAportes() {
        return aportes;
    }

    public void setAportes(BigDecimal aportes) {
        this.aportes = aportes;
    }
}
