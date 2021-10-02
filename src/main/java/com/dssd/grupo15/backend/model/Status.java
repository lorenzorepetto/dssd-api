package com.dssd.grupo15.backend.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name="sociedad_anonima_id", nullable=false)
    private SociedadAnonima sociedadAnonima;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    public Status() {
    }

    public Status(String status, SociedadAnonima sociedadAnonima, LocalDateTime dateCreated) {
        this.status = status;
        this.sociedadAnonima = sociedadAnonima;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SociedadAnonima getSociedadAnonima() {
        return sociedadAnonima;
    }

    public void setSociedadAnonima(SociedadAnonima sociedadAnonima) {
        this.sociedadAnonima = sociedadAnonima;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
