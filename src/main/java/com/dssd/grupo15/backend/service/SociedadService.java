package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.rest.request.EstadoDTO;
import com.dssd.grupo15.backend.dto.rest.request.PaisDTO;
import com.dssd.grupo15.backend.dto.rest.request.SociedadAnonimaDTO;
import com.dssd.grupo15.backend.dto.rest.request.SocioDTO;
import com.dssd.grupo15.backend.model.*;
import com.dssd.grupo15.backend.repository.ExportacionRepository;
import com.dssd.grupo15.backend.repository.SociedadAnonimaRepository;
import com.dssd.grupo15.backend.repository.SocioRepository;
import com.dssd.grupo15.backend.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SociedadService {

    private static final String CREATE_URL = "http://localhost:8080/bonita/API/bpm/case";
    private static final String TOKEN_COOKIE = "X-Bonita-API-Token";
    private final RestTemplate restTemplate;

    private final PaisService paisService;
    private final SocioRepository socioRepository;
    private final SociedadAnonimaRepository sociedadAnonimaRepository;
    private final StatusRepository statusRepository;
    private final ExportacionRepository exportacionRepository;

    @Autowired
    public SociedadService(PaisService paisService,
                           SocioRepository socioRepository,
                           SociedadAnonimaRepository sociedadAnonimaRepository,
                           StatusRepository statusRepository,
                           ExportacionRepository exportacionRepository) {
        this.paisService = paisService;
        this.socioRepository = socioRepository;
        this.sociedadAnonimaRepository = sociedadAnonimaRepository;
        this.statusRepository = statusRepository;
        this.exportacionRepository = exportacionRepository;
        this.restTemplate = new RestTemplate();
    }

    public Object createSociedad(SociedadAnonimaDTO sociedadAnonimaDTO, MultipartFile file, String token) {

        this.createSociedadFromDTO(sociedadAnonimaDTO);
        return this.sociedadAnonimaRepository.findByNombre(sociedadAnonimaDTO.getNombre());
    }

    private void createSociedadFromDTO(SociedadAnonimaDTO sociedadAnonimaDTO) {
        SociedadAnonima sociedadAnonima = new SociedadAnonima();
        sociedadAnonima.setNombre(sociedadAnonimaDTO.getNombre());
        sociedadAnonima.setDomicilioLegal(sociedadAnonimaDTO.getDomicilioLegal());
        sociedadAnonima.setDomicilioReal(sociedadAnonimaDTO.getDomicilioReal());
        sociedadAnonima.setFechaCreacion(sociedadAnonimaDTO.getFechaCreacion());
        sociedadAnonima.setEmail(sociedadAnonimaDTO.getEmail());
        sociedadAnonima.setProcessId("id de proceso"); // TODO: arreglar

        List<Socio> socios = new ArrayList<>();
        for (SocioDTO socioDTO: sociedadAnonimaDTO.getSocios()) {
            socios.add(this.socioRepository.save(new Socio(socioDTO.getNombre(),
                    socioDTO.getApellido(),
                    socioDTO.getAportes())));
        }
        sociedadAnonima.setSocios(socios);
        sociedadAnonima.setApoderado(socios.stream()
                .max(Comparator.comparing(Socio::getAportes))
                .orElseThrow(() -> new RuntimeException(""))); // TODO: arreglar

        SociedadAnonima newSociedad = this.sociedadAnonimaRepository.save(sociedadAnonima);

        // Status
        List<Status> statusList = new ArrayList<>();
        statusList.add(this.statusRepository.save(Status.Builder.aStatus()
                            .status("NEW")
                            .dateCreated(LocalDateTime.now())
                            .sociedadAnonima(newSociedad).build()));
        newSociedad.setStatus(statusList);

        // Exportaciones
        List<Exportacion> exportaciones = new ArrayList<>();
        for (PaisDTO paisDTO: sociedadAnonimaDTO.getPaises()) {
            Pais pais = this.paisService.handleCreation(paisDTO);
            if (paisDTO.getStates().isEmpty()) {
                exportaciones.add(this.exportacionRepository.save(new Exportacion(newSociedad, pais)));
            } else {
                for (EstadoDTO estadoDTO: paisDTO.getStates()) {
                    exportaciones.add(this.exportacionRepository.save(new Exportacion(newSociedad, pais, estadoDTO.getName())));
                }
            }
        }
        newSociedad.setExportaciones(exportaciones);

        this.sociedadAnonimaRepository.save(newSociedad);
    }
}
