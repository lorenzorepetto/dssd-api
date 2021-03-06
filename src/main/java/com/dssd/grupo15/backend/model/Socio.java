package com.dssd.grupo15.backend.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "socios")
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "aportes", nullable = false)
    private BigDecimal aportes;

    @ManyToOne
    @JoinColumn(name="sociedad_anonima_id", nullable=false)
    private SociedadAnonima sociedadAnonima;

    public Socio() {
    }

    public Socio(String nombre, String apellido, BigDecimal aportes, SociedadAnonima sociedadAnonima) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.aportes = aportes;
        this.sociedadAnonima = sociedadAnonima;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public SociedadAnonima getSociedadAnonima() {
        return sociedadAnonima;
    }

    public void setSociedadAnonima(SociedadAnonima sociedadAnonima) {
        this.sociedadAnonima = sociedadAnonima;
    }
}
