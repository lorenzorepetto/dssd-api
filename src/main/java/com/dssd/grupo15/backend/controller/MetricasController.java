package com.dssd.grupo15.backend.controller;

import com.dssd.grupo15.backend.dto.metric.MetricasDTO;
import com.dssd.grupo15.backend.exception.InvalidCredentialsException;
import com.dssd.grupo15.backend.service.MetricasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricasController extends GenericController {

    private final MetricasService metricasService;

    @Autowired
    public MetricasController(MetricasService metricasService) {
        this.metricasService = metricasService;
    }

    @GetMapping("/api/metricas")
    public MetricasDTO getMetricas(@RequestHeader(BONITA_TOKEN) String token,
                                   @RequestHeader(SESSION_ID_COOKIE) String sessionId,
                                   @RequestHeader(ROLE) String role) throws InvalidCredentialsException {
        return metricasService.getMetricas(role);
    }
}
