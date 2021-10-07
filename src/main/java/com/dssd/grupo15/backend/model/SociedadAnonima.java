package com.dssd.grupo15.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sociedades_anonimas")
public class SociedadAnonima {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "process_id", nullable = false)
    private String processId;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "domicilio_legal", nullable = false)
    private String domicilioLegal;

    @Column(name = "domicilio_real", nullable = false)
    private String domicilioReal;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "sociedadAnonima"
    )
    @JsonIgnoreProperties("sociedadAnonima")
    private List<Status> status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "apoderado_id", referencedColumnName = "id")
    @JsonIgnoreProperties("sociedadAnonima")
    private Socio apoderado;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "sociedadAnonima"
    )
    @JsonIgnoreProperties("sociedadAnonima")
    private List<Socio> socios;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File estatuto;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "sociedadAnonima"
    )
    @JsonIgnoreProperties("sociedadAnonima")
    private List<Exportacion> exportaciones;

    public SociedadAnonima() {
    }

    public SociedadAnonima(String processId, String nombre, LocalDate fechaCreacion, String domicilioLegal, String domicilioReal, String email, List<Status> status, Socio apoderado, List<Socio> socios, File estatuto, List<Exportacion> exportaciones) {
        this.processId = processId;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.domicilioLegal = domicilioLegal;
        this.domicilioReal = domicilioReal;
        this.email = email;
        this.status = status;
        this.apoderado = apoderado;
        this.socios = socios;
        this.estatuto = estatuto;
        this.exportaciones = exportaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDomicilioLegal() {
        return domicilioLegal;
    }

    public void setDomicilioLegal(String domicilioLegal) {
        this.domicilioLegal = domicilioLegal;
    }

    public String getDomicilioReal() {
        return domicilioReal;
    }

    public void setDomicilioReal(String domicilioReal) {
        this.domicilioReal = domicilioReal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public Socio getApoderado() {
        return apoderado;
    }

    public void setApoderado(Socio apoderado) {
        this.apoderado = apoderado;
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public void setSocios(List<Socio> socios) {
        this.socios = socios;
    }

    public File getEstatuto() {
        return estatuto;
    }

    public void setEstatuto(File estatuto) {
        this.estatuto = estatuto;
    }

    public List<Exportacion> getExportaciones() {
        return exportaciones;
    }

    public void setExportaciones(List<Exportacion> exportaciones) {
        this.exportaciones = exportaciones;
    }


    public static final class Builder {
        private SociedadAnonima sociedadAnonima;

        private Builder() {
            sociedadAnonima = new SociedadAnonima();
        }

        public static Builder aSociedadAnonima() {
            return new Builder();
        }

        public Builder id(Long id) {
            sociedadAnonima.setId(id);
            return this;
        }

        public Builder processId(String processId) {
            sociedadAnonima.setProcessId(processId);
            return this;
        }

        public Builder nombre(String nombre) {
            sociedadAnonima.setNombre(nombre);
            return this;
        }

        public Builder fechaCreacion(LocalDate fechaCreacion) {
            sociedadAnonima.setFechaCreacion(fechaCreacion);
            return this;
        }

        public Builder domicilioLegal(String domicilioLegal) {
            sociedadAnonima.setDomicilioLegal(domicilioLegal);
            return this;
        }

        public Builder domicilioReal(String domicilioReal) {
            sociedadAnonima.setDomicilioReal(domicilioReal);
            return this;
        }

        public Builder email(String email) {
            sociedadAnonima.setEmail(email);
            return this;
        }

        public Builder status(List<Status> status) {
            sociedadAnonima.setStatus(status);
            return this;
        }

        public Builder apoderado(Socio apoderado) {
            sociedadAnonima.setApoderado(apoderado);
            return this;
        }

        public Builder socios(List<Socio> socios) {
            sociedadAnonima.setSocios(socios);
            return this;
        }

        public Builder estatuto(File estatuto) {
            sociedadAnonima.setEstatuto(estatuto);
            return this;
        }

        public Builder exportaciones(List<Exportacion> exportaciones) {
            sociedadAnonima.setExportaciones(exportaciones);
            return this;
        }

        public SociedadAnonima build() {
            return sociedadAnonima;
        }
    }
}
