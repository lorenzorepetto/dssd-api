package com.dssd.grupo15.backend.dto.rest.bonita;

public class InitProcessResponseDTO {
    private String id;
    private String state;

    public InitProcessResponseDTO() {
    }

    public InitProcessResponseDTO(String id, String state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
