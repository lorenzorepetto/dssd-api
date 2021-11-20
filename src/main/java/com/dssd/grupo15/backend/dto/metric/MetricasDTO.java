package com.dssd.grupo15.backend.dto.metric;

import com.dssd.grupo15.backend.model.Continente;
import com.dssd.grupo15.backend.model.Pais;

import java.util.List;
import java.util.Map;

public class MetricasDTO {
    private List<PaisMetricaDTO> paisesMaxExportaciones;
    private List<Continente> continentesMaxExportaciones;
    private List<Continente> continentesConExportaciones;

    private Map<String, Integer> statusMap;
    private double tiempoPromedioProcesos;

    public List<PaisMetricaDTO> getPaisesMaxExportaciones() {
        return paisesMaxExportaciones;
    }

    public void setPaisesMaxExportaciones(List<PaisMetricaDTO> paisesMaxExportaciones) {
        this.paisesMaxExportaciones = paisesMaxExportaciones;
    }

    public List<Continente> getContinentesMaxExportaciones() {
        return continentesMaxExportaciones;
    }

    public void setContinentesMaxExportaciones(List<Continente> continentesMaxExportaciones) {
        this.continentesMaxExportaciones = continentesMaxExportaciones;
    }

    public List<Continente> getContinentesConExportaciones() {
        return continentesConExportaciones;
    }

    public void setContinentesConExportaciones(List<Continente> continentesConExportaciones) {
        this.continentesConExportaciones = continentesConExportaciones;
    }

    public Map<String, Integer> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<String, Integer> statusMap) {
        this.statusMap = statusMap;
    }

    public double getTiempoPromedioProcesos() {
        return tiempoPromedioProcesos;
    }

    public void setTiempoPromedioProcesos(double tiempoPromedioProcesos) {
        this.tiempoPromedioProcesos = tiempoPromedioProcesos;
    }

    public static final class Builder {
        private MetricasDTO metricasDTO;

        private Builder() {
            metricasDTO = new MetricasDTO();
        }

        public static Builder aMetricasDTO() {
            return new Builder();
        }

        public Builder paisesMaxExportaciones(List<PaisMetricaDTO> paisesMaxExportaciones) {
            metricasDTO.setPaisesMaxExportaciones(paisesMaxExportaciones);
            return this;
        }

        public Builder continentesMaxExportaciones(List<Continente> continentesMaxExportaciones) {
            metricasDTO.setContinentesMaxExportaciones(continentesMaxExportaciones);
            return this;
        }

        public Builder continentesConExportaciones(List<Continente> continentesConExportaciones) {
            metricasDTO.setContinentesConExportaciones(continentesConExportaciones);
            return this;
        }

        public Builder statusMap(Map<String, Integer> statusMap) {
            metricasDTO.setStatusMap(statusMap);
            return this;
        }

        public Builder tiempoPromedioProcesos(double tiempoPromedioProcesos) {
            metricasDTO.setTiempoPromedioProcesos(tiempoPromedioProcesos);
            return this;
        }

        public MetricasDTO build() {
            return metricasDTO;
        }
    }
}
