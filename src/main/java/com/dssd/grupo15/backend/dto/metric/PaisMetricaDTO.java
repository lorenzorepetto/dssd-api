package com.dssd.grupo15.backend.dto.metric;

import com.dssd.grupo15.backend.model.Pais;

public class PaisMetricaDTO {
    private Long count;
    private Pais pais;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public static final class Builder {
        private PaisMetricaDTO paisMetricaDTO;

        private Builder() {
            paisMetricaDTO = new PaisMetricaDTO();
        }

        public static Builder aPaisMetricaDTO() {
            return new Builder();
        }

        public Builder count(Long count) {
            paisMetricaDTO.setCount(count);
            return this;
        }

        public Builder pais(Pais pais) {
            paisMetricaDTO.setPais(pais);
            return this;
        }

        public PaisMetricaDTO build() {
            return paisMetricaDTO;
        }
    }
}
