package com.dssd.grupo15.backend.dto.rest.request;

import java.util.List;

public class PaisDTO {
    private String code;
    private String name;
    private ContinenteDTO continent;
    private List<LenguajeDTO> languages;
    private List<EstadoDTO> states;

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

    public List<LenguajeDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LenguajeDTO> languages) {
        this.languages = languages;
    }

    public List<EstadoDTO> getStates() {
        return states;
    }

    public void setStates(List<EstadoDTO> states) {
        this.states = states;
    }

    public ContinenteDTO getContinent() {
        return continent;
    }

    public void setContinent(ContinenteDTO continent) {
        this.continent = continent;
    }


    public static final class Builder {
        private PaisDTO paisDTO;

        private Builder() {
            paisDTO = new PaisDTO();
        }

        public static Builder aPaisDTO() {
            return new Builder();
        }

        public Builder code(String code) {
            paisDTO.setCode(code);
            return this;
        }

        public Builder name(String name) {
            paisDTO.setName(name);
            return this;
        }

        public Builder continent(ContinenteDTO continent) {
            paisDTO.setContinent(continent);
            return this;
        }

        public Builder languages(List<LenguajeDTO> languages) {
            paisDTO.setLanguages(languages);
            return this;
        }

        public Builder states(List<EstadoDTO> states) {
            paisDTO.setStates(states);
            return this;
        }

        public PaisDTO build() {
            return paisDTO;
        }
    }
}
