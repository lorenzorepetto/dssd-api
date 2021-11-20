package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.metric.MetricasDTO;
import com.dssd.grupo15.backend.dto.metric.PaisMetricaDTO;
import com.dssd.grupo15.backend.model.Continente;
import com.dssd.grupo15.backend.model.Pais;
import com.dssd.grupo15.backend.model.SociedadAnonima;
import com.dssd.grupo15.backend.model.enums.StatusEnum;
import com.dssd.grupo15.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetricasService {

    private final ExportacionRepository exportacionRepository;
    private final ContinenteRepository continenteRepository;
    private final StatusRepository statusRepository;
    private final SociedadAnonimaRepository sociedadAnonimaRepository;
    private final PaisRepository paisRepository;

    @Autowired
    public MetricasService(ExportacionRepository exportacionRepository, ContinenteRepository continenteRepository, StatusRepository statusRepository, SociedadAnonimaRepository sociedadAnonimaRepository, PaisRepository paisRepository) {
        this.exportacionRepository = exportacionRepository;
        this.continenteRepository = continenteRepository;
        this.statusRepository = statusRepository;
        this.sociedadAnonimaRepository = sociedadAnonimaRepository;
        this.paisRepository = paisRepository;
    }

    @Transactional
    public MetricasDTO getMetricas() {
        List<Continente> continentesConMasExportaciones = this.continenteRepository.continentesConMasExportacionesExceptoAmerica();
        List<Map<String, Object>> paisesConMasExportacionesMap = this.exportacionRepository.paisesConMasExportaciones();
        List<PaisMetricaDTO> paisesConMasExportaciones = paisesConMasExportacionesMap.stream()
                .map(result -> PaisMetricaDTO.Builder.aPaisMetricaDTO()
                        .pais(this.paisRepository.findById(((BigInteger) result.get("pais_id")).longValue()).orElse(null))
                        .count(((BigInteger) result.get("cant")).longValue())
                        .build())
                .collect(Collectors.toList());

        Map<String, Integer> statusMap = new HashMap<>();
        statusMap.put(StatusEnum.MESA_ENTRADAS_APROBADO.name(), this.statusRepository.countByStatus(StatusEnum.MESA_ENTRADAS_APROBADO.name()));
        statusMap.put(StatusEnum.MESA_ENTRADAS_RECHAZADO.name(), this.statusRepository.countByStatus(StatusEnum.MESA_ENTRADAS_RECHAZADO.name()));
        statusMap.put(StatusEnum.LEGALES_APROBADO.name(), this.statusRepository.countByStatus(StatusEnum.LEGALES_APROBADO.name()));
        statusMap.put(StatusEnum.LEGALES_RECHAZADO.name(), this.statusRepository.countByStatus(StatusEnum.LEGALES_RECHAZADO.name()));

        List<SociedadAnonima> sociedadesAnonimasFinalizadas = this.sociedadAnonimaRepository.findByStatus(StatusEnum.FINALIZADO.name());
        List<Long> periods = sociedadesAnonimasFinalizadas.stream().map(sociedadAnonima -> {
            LocalDateTime stopDate = sociedadAnonima.getStatus().get(0).getDateCreated();
            LocalDateTime startDate = sociedadAnonima.getStatus().get(sociedadAnonima.getStatus().size()-1).getDateCreated();
            return ChronoUnit.DAYS.between(startDate.toLocalDate(), stopDate.toLocalDate());
        }).collect(Collectors.toList());
        double average = periods.stream().mapToInt(Long::intValue).average().orElse(0);

        return MetricasDTO.Builder.aMetricasDTO()
                .continentesConExportaciones(this.continenteRepository.findAll())
                .continentesMaxExportaciones(continentesConMasExportaciones)
                .paisesMaxExportaciones(paisesConMasExportaciones.subList(0, 5))
                .statusMap(statusMap)
                .tiempoPromedioProcesos(average)
                .build();
    }
}
