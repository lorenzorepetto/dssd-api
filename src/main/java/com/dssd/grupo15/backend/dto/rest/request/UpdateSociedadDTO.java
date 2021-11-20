package com.dssd.grupo15.backend.dto.rest.request;

public class UpdateSociedadDTO {
    CredentialsDTO credentials;
    SociedadAnonimaUpdateDTO sociedadAnonima;

    public CredentialsDTO getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsDTO credentials) {
        this.credentials = credentials;
    }

    public SociedadAnonimaUpdateDTO getSociedadAnonima() {
        return sociedadAnonima;
    }

    public void setSociedadAnonima(SociedadAnonimaUpdateDTO sociedadAnonima) {
        this.sociedadAnonima = sociedadAnonima;
    }
}
