package com.dssd.grupo15.backend.dto.rest.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class SociedadAnonimaUpdateDTO {
    private String nombre;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaCreacion;
    private String domicilioLegal;
    private String domicilioReal;
    private String email;

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
}
