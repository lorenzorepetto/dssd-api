package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.dto.rest.bonita.VariableDTO;
import com.dssd.grupo15.backend.dto.rest.request.*;
import com.dssd.grupo15.backend.exception.AlreadyExistsException;
import com.dssd.grupo15.backend.exception.common.GenericException;
import com.dssd.grupo15.backend.model.*;
import com.dssd.grupo15.backend.model.enums.Role;
import com.dssd.grupo15.backend.model.enums.StatusEnum;
import com.dssd.grupo15.backend.repository.*;
import com.dssd.grupo15.backend.utils.StatusUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    private final ExpedienteRepository expedienteRepository;

    @Autowired
    public SociedadService(PaisService paisService,
                           BonitaApiService bonitaApiService,
                           FilesStorageService filesStorageService,
                           SocioRepository socioRepository,
                           SociedadAnonimaRepository sociedadAnonimaRepository,
                           StatusRepository statusRepository,
                           ExportacionRepository exportacionRepository,
                           ExpedienteRepository expedienteRepository) {
        this.paisService = paisService;
        this.bonitaApiService = bonitaApiService;
        this.filesStorageService = filesStorageService;
        this.socioRepository = socioRepository;
        this.sociedadAnonimaRepository = sociedadAnonimaRepository;
        this.statusRepository = statusRepository;
        this.exportacionRepository = exportacionRepository;
        this.expedienteRepository = expedienteRepository;
    }


    public List<SociedadAnonima> getSociedadesByRole(String role, String username) {
        if (!EnumUtils.isValidEnum(Role.class, role)) {
            return new ArrayList<>();
        }
        if (Role.LEGALES.name().equalsIgnoreCase(role)) {
            return this.sociedadAnonimaRepository.findByStatus(StatusEnum.MESA_ENTRADAS_APROBADO.name());
        } else if (Role.MESA_ENTRADAS.name().equalsIgnoreCase(role)) {
            return this.sociedadAnonimaRepository.findByStatus(StatusEnum.NEW.name());
        } else {
            return this.sociedadAnonimaRepository.findByUsername(username);
        }
    }

    @Transactional
    public SociedadAnonima updateSociedadStatus(Long id, boolean aprobado, CredentialsDTO credentialsDTO, String token, String sessionId) throws GenericException {
        Optional<SociedadAnonima> sociedadAnonimaOptional = this.sociedadAnonimaRepository.findById(id);
        //errores?
        if (sociedadAnonimaOptional.isEmpty()) {
            return null;
        }

        SociedadAnonima sociedadAnonima = sociedadAnonimaOptional.get();

        Status newStatus = this.statusRepository.save(Status.Builder.aStatus()
                        .status(StatusUtils.getNextStatus(sociedadAnonima.getStatus().get(0), aprobado))
                        .dateCreated(LocalDateTime.now())
                        .sociedadAnonima(sociedadAnonima).build());
        sociedadAnonima.getStatus().add(newStatus);

        this.manageNewStatusActions(newStatus, sociedadAnonima, credentialsDTO, token, sessionId);

        this.sociedadAnonimaRepository.save(sociedadAnonima);
        sociedadAnonima.getStatus().sort(Comparator.comparing(Status::getDateCreated).reversed());
        return sociedadAnonima;
    }

    @Transactional
    public SociedadAnonima createSociedad(SociedadAnonimaDTO sociedadAnonimaDTO, MultipartFile file, String token, String sessionId) throws GenericException {
        if (this.sociedadAnonimaRepository.findByNombre(sociedadAnonimaDTO.getNombre()).isPresent()) {
            throw new AlreadyExistsException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(String.format("Sociedad with nombre %s already exists.", sociedadAnonimaDTO.getNombre()))
                    .build());
        }

        String processId = this.bonitaApiService.initBonitaProcess(PROCESS_NAME,
                this.getInitVariables(sociedadAnonimaDTO),
                token,
                sessionId);

        File savedFile = this.filesStorageService.save(file, sociedadAnonimaDTO);
        return this.createAndSaveSociedad(sociedadAnonimaDTO, savedFile, processId);
    }

    private List<VariableDTO> getInitVariables(SociedadAnonimaDTO sociedadAnonimaDTO) {
        List<VariableDTO> variables = new ArrayList<>();
        variables.add(new VariableDTO("fecha_creacion", sociedadAnonimaDTO.getFechaCreacion().toString()));
        variables.add(new VariableDTO("nombre_sa", sociedadAnonimaDTO.getNombre()));
        variables.add(new VariableDTO("status", StatusEnum.NEW.name()));
        variables.add(new VariableDTO("email_apoderado", sociedadAnonimaDTO.getEmail()));
        return variables;
    }

    private SociedadAnonima createAndSaveSociedad(SociedadAnonimaDTO sociedadAnonimaDTO, File file, String processId) {
        SociedadAnonima sociedadAnonima = new SociedadAnonima();
        sociedadAnonima.setNombre(sociedadAnonimaDTO.getNombre());
        sociedadAnonima.setDomicilioLegal(sociedadAnonimaDTO.getDomicilioLegal());
        sociedadAnonima.setDomicilioReal(sociedadAnonimaDTO.getDomicilioReal());
        sociedadAnonima.setFechaCreacion(sociedadAnonimaDTO.getFechaCreacion());
        sociedadAnonima.setEmail(sociedadAnonimaDTO.getEmail());
        sociedadAnonima.setUsername(sociedadAnonimaDTO.getUsername());

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

    private void manageNewStatusActions(Status newStatus, SociedadAnonima sociedadAnonima, CredentialsDTO credentialsDTO, String token, String sessionId)
            throws GenericException {
        if (StatusEnum.MESA_ENTRADAS_APROBADO.name().equalsIgnoreCase(newStatus.getStatus())) {
            // generar expediente
            Expediente expediente = this.expedienteRepository.save(Expediente.Builder.anExpediente()
                            .sociedadAnonima(sociedadAnonima)
                            .username(credentialsDTO.getUsername()).build());
            sociedadAnonima.setExpediente(expediente);
            this.sociedadAnonimaRepository.save(sociedadAnonima);

            // actualizar tarea en bonita
            this.bonitaApiService.updateTask(sociedadAnonima, newStatus.getStatus(), credentialsDTO, token, sessionId);
        } else if (StatusEnum.MESA_ENTRADAS_RECHAZADO.name().equalsIgnoreCase(newStatus.getStatus())) {
            // TODO: enviar mail?
        } else if (StatusEnum.LEGALES_APROBADO.name().equalsIgnoreCase(newStatus.getStatus())) {
            // TODO: estampillado
        } else if (StatusEnum.LEGALES_RECHAZADO.name().equalsIgnoreCase(newStatus.getStatus())) {
            // TODO: enviar mail?
        }
    }
}
