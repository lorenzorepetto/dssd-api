package com.dssd.grupo15.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "expedientes")
public class Expediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sociedad_anonima_id", referencedColumnName = "id")
    private SociedadAnonima sociedadAnonima;

    @Column
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static final class Builder {
        private Expediente expediente;

        private Builder() {
            expediente = new Expediente();
        }

        public static Builder anExpediente() {
            return new Builder();
        }

        public Builder id(Long id) {
            expediente.setId(id);
            return this;
        }

        public Builder sociedadAnonima(SociedadAnonima sociedadAnonima) {
            expediente.setSociedadAnonima(sociedadAnonima);
            return this;
        }

        public Builder username(String username) {
            expediente.setUsername(username);
            return this;
        }

        public Expediente build() {
            return expediente;
        }
    }
}
