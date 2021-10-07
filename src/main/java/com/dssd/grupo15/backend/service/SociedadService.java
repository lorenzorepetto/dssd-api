package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.dto.rest.bonita.VariableDTO;
import com.dssd.grupo15.backend.dto.rest.request.EstadoDTO;
import com.dssd.grupo15.backend.dto.rest.request.PaisDTO;
import com.dssd.grupo15.backend.dto.rest.request.SociedadAnonimaDTO;
import com.dssd.grupo15.backend.dto.rest.request.SocioDTO;
import com.dssd.grupo15.backend.exception.AlreadyExistsException;
import com.dssd.grupo15.backend.exception.common.GenericException;
import com.dssd.grupo15.backend.model.*;
import com.dssd.grupo15.backend.model.enums.StatusEnum;
import com.dssd.grupo15.backend.repository.ExportacionRepository;
import com.dssd.grupo15.backend.repository.SociedadAnonimaRepository;
import com.dssd.grupo15.backend.repository.SocioRepository;
import com.dssd.grupo15.backend.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SociedadService {

    private static final String PROCESS_NAME = "creacion_sociedad_anonima";

    private final PaisService paisService;
    private final BonitaApiService bonitaApiService;
    private final FilesStorageService filesStorageService;
    private final SocioRepository socioRepository;
    private final SociedadAnonimaRepository sociedadAnonimaRepository;
    private final StatusRepository statusRepository;
    private final ExportacionRepository exportacionRepository;

    @Autowired
    public SociedadService(PaisService paisService,
                           BonitaApiService bonitaApiService,
                           FilesStorageService filesStorageService,
                           SocioRepository socioRepository,
                           SociedadAnonimaRepository sociedadAnonimaRepository,
                           StatusRepository statusRepository,
                           ExportacionRepository exportacionRepository) {
        this.paisService = paisService;
        this.bonitaApiService = bonitaApiService;
        this.filesStorageService = filesStorageService;
        this.socioRepository = socioRepository;
        this.sociedadAnonimaRepository = sociedadAnonimaRepository;
        this.statusRepository = statusRepository;
        this.exportacionRepository = exportacionRepository;
    }

    @Transactional
    public Object createSociedad(SociedadAnonimaDTO sociedadAnonimaDTO, MultipartFile file, String token, String sessionId) throws GenericException {
        if (this.sociedadAnonimaRepository.findByNombre(sociedadAnonimaDTO.getNombre()).isPresent()) {
            throw new AlreadyExistsException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(String.format("Sociedad with nombre %s already exists.", sociedadAnonimaDTO.getNombre()))
                    .build());
        }

        String processId = this.bonitaApiService.initBonitaProcess(PROCESS_NAME,
                this.getVariables(sociedadAnonimaDTO),
                token,
                sessionId);

        File savedFile = this.filesStorageService.save(file, sociedadAnonimaDTO);
        return this.createAndSaveSociedad(sociedadAnonimaDTO, savedFile, processId);
    }

    private List<VariableDTO> getVariables(SociedadAnonimaDTO sociedadAnonimaDTO) {
        List<VariableDTO> variables = new ArrayList<>();
        variables.add(new VariableDTO("fecha_creacion", sociedadAnonimaDTO.getFechaCreacion().toString()));
        return variables;
    }

    private SociedadAnonima createAndSaveSociedad(SociedadAnonimaDTO sociedadAnonimaDTO, File file, String processId) {
        SociedadAnonima sociedadAnonima = new SociedadAnonima();
        sociedadAnonima.setNombre(sociedadAnonimaDTO.getNombre());
        sociedadAnonima.setDomicilioLegal(sociedadAnonimaDTO.getDomicilioLegal());
        sociedadAnonima.setDomicilioReal(sociedadAnonimaDTO.getDomicilioReal());
        sociedadAnonima.setFechaCreacion(sociedadAnonimaDTO.getFechaCreacion());
        sociedadAnonima.setEmail(sociedadAnonimaDTO.getEmail());

        // File y ProcessId
        sociedadAnonima.setEstatuto(file);
        sociedadAnonima.setProcessId(processId);

        SociedadAnonima newSociedad = this.sociedadAnonimaRepository.save(sociedadAnonima);

        // Status
        List<Status> statusList = new ArrayList<>();
        statusList.add(this.statusRepository.save(Status.Builder.aStatus()
                .status(StatusEnum.NEW.name())
                .dateCreated(LocalDateTime.now())
                .sociedadAnonima(newSociedad).build()));
        newSociedad.setStatus(statusList);

        // Exportaciones
        List<Exportacion> exportaciones = new ArrayList<>();
        for (PaisDTO paisDTO : sociedadAnonimaDTO.getPaises()) {
            Pais pais = this.paisService.handleCreation(paisDTO);
            if (paisDTO.getStates().isEmpty()) {
                exportaciones.add(this.exportacionRepository.save(new Exportacion(newSociedad, pais)));
            } else {
                for (EstadoDTO estadoDTO : paisDTO.getStates()) {
                    exportaciones.add(this.exportacionRepository.save(new Exportacion(newSociedad, pais, estadoDTO.getName())));
                }
            }
        }
        newSociedad.setExportaciones(exportaciones);

        // Socios
        List<Socio> socios = new ArrayList<>();
        for (SocioDTO socioDTO : sociedadAnonimaDTO.getSocios()) {
            socios.add(this.socioRepository.save(new Socio(socioDTO.getNombre(),
                    socioDTO.getApellido(),
                    socioDTO.getAportes(),
                    newSociedad)));
        }
        newSociedad.setSocios(socios);
        newSociedad.setApoderado(socios.stream()
                .max(Comparator.comparing(Socio::getAportes))
                .orElseThrow(() -> new RuntimeException(""))); // TODO: arreglar

        return this.sociedadAnonimaRepository.save(newSociedad);
    }
}
