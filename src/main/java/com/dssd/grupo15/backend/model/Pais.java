package com.dssd.grupo15.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "paises")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "paises_lenguajes",
            joinColumns = @JoinColumn(name = "pais_id"),
            inverseJoinColumns = @JoinColumn(name = "lenguaje_id"))
    private List<Lenguaje> languages;

    @ManyToOne
    @JoinColumn(name="continente_id", nullable=false)
    private Continente continent;

    public Pais() {
    }

    public Pais(String code, String name, Continente continent) {
        this.code = code;
        this.name = name;
        this.continent = continent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lenguaje> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Lenguaje> languages) {
        this.languages = languages;
    }

    public Continente getContinent() {
        return continent;
    }

    public void setContinent(Continente continent) {
        this.continent = continent;
    }
}
