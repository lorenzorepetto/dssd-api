package com.dssd.grupo15.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "exportaciones")
public class Exportacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="sociedad_anonima_id", nullable=false)
    private SociedadAnonima sociedadAnonima;

    @ManyToOne
    @JoinColumn(name="pais_id", nullable=false)
    private Pais pais;

    @Column()
    private String estado;

    public Exportacion() {
    }

    public Exportacion(SociedadAnonima sociedadAnonima, Pais pais) {
        this.sociedadAnonima = sociedadAnonima;
        this.pais = pais;
    }

    public Exportacion(SociedadAnonima sociedadAnonima, Pais pais, String estado) {
        this.sociedadAnonima = sociedadAnonima;
        this.pais = pais;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SociedadAnonima getSociedadAnonima() {
        return sociedadAnonima;
    }

    public void setSociedadAnonima(SociedadAnonima sociedadAnonima) {
        this.sociedadAnonima = sociedadAnonima;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
