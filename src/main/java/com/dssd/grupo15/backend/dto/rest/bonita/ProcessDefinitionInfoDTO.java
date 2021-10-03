package com.dssd.grupo15.backend.dto.rest.bonita;

public class ProcessDefinitionInfoDTO {
    private String id;

    public ProcessDefinitionInfoDTO() {
    }

    public ProcessDefinitionInfoDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
