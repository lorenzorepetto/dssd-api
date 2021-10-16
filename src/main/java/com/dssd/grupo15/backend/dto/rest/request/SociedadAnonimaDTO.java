package com.dssd.grupo15.backend.dto.rest.request;

import java.time.LocalDate;
import java.util.List;

public class SociedadAnonimaDTO {
    private String nombre;
    private LocalDate fechaCreacion;
    private String domicilioLegal;
    private String domicilioReal;
    private String email;
    private String username;
    private SocioDTO apoderado;
    private List<SocioDTO> socios;
    private List<PaisDTO> paises;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SocioDTO getApoderado() {
        return apoderado;
    }

    public void setApoderado(SocioDTO apoderado) {
        this.apoderado = apoderado;
    }

    public List<SocioDTO> getSocios() {
        return socios;
    }

    public void setSocios(List<SocioDTO> socios) {
        this.socios = socios;
    }

    public List<PaisDTO> getPaises() {
        return paises;
    }

    public void setPaises(List<PaisDTO> paises) {
        this.paises = paises;
    }

    public static final class Builder {
        private SociedadAnonimaDTO sociedadAnonimaDTO;

        private Builder() {
            sociedadAnonimaDTO = new SociedadAnonimaDTO();
        }

        public static Builder aSociedadAnonimaDTO() {
            return new Builder();
        }

        public Builder nombre(String nombre) {
            sociedadAnonimaDTO.setNombre(nombre);
            return this;
        }

        public Builder fechaCreacion(LocalDate fechaCreacion) {
            sociedadAnonimaDTO.setFechaCreacion(fechaCreacion);
            return this;
        }

        public Builder domicilioLegal(String domicilioLegal) {
            sociedadAnonimaDTO.setDomicilioLegal(domicilioLegal);
            return this;
        }

        public Builder domicilioReal(String domicilioReal) {
            sociedadAnonimaDTO.setDomicilioReal(domicilioReal);
            return this;
        }

        public Builder email(String email) {
            sociedadAnonimaDTO.setEmail(email);
            return this;
        }

        public Builder username(String username) {
            sociedadAnonimaDTO.setUsername(username);
            return this;
        }

        public Builder apoderado(SocioDTO apoderado) {
            sociedadAnonimaDTO.setApoderado(apoderado);
            return this;
        }

        public Builder socios(List<SocioDTO> socios) {
            sociedadAnonimaDTO.setSocios(socios);
            return this;
        }

        public Builder paises(List<PaisDTO> paises) {
            sociedadAnonimaDTO.setPaises(paises);
            return this;
        }

        public SociedadAnonimaDTO build() {
            return sociedadAnonimaDTO;
        }
    }
}
