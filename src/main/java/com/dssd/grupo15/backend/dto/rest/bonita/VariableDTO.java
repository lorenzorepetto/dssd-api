package com.dssd.grupo15.backend.dto.rest.bonita;

public class VariableDTO {
    private String name;
    private String value;

    public VariableDTO() {
    }

    public VariableDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
