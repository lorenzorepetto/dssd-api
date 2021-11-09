package com.dssd.grupo15.backend.controller;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.dto.rest.request.CredentialsDTO;
import com.dssd.grupo15.backend.dto.rest.request.SociedadAnonimaDTO;
import com.dssd.grupo15.backend.exception.BadRequestException;
import com.dssd.grupo15.backend.exception.common.GenericException;
import com.dssd.grupo15.backend.model.SociedadAnonima;
import com.dssd.grupo15.backend.service.SociedadService;
import com.dssd.grupo15.backend.utils.SociedadMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class SociedadController extends GenericController {

    private final SociedadService sociedadService;
    private static final String BONITA_TOKEN = "X-Bonita-API-Token";
    private static final String ROLE = "ROLE";
    private static final String SESSION_ID_COOKIE = "JSESSIONID";

    private static final Logger logger = LoggerFactory.getLogger(SociedadController.class);

    @Autowired
    public SociedadController(SociedadService sociedadService) {
        this.sociedadService = sociedadService;
    }

    @GetMapping("/api/sociedades")
    public List<SociedadAnonima> getSociedades(@RequestHeader(BONITA_TOKEN) String token,
                                               @RequestHeader(SESSION_ID_COOKIE) String sessionId,
                                               @RequestHeader(ROLE) String role,
                                               @RequestParam(required = false) String username) {
        return this.sociedadService.getSociedadesByRole(role, username);
    }

    @PostMapping("/api/sociedad")
    public SociedadAnonima createSociedad(@RequestParam("estatuto") MultipartFile file,
                                          @RequestParam("sociedad_anonima") String sociedadAnonimaString,
                                          @RequestHeader(BONITA_TOKEN) String token,
                                          @RequestHeader(SESSION_ID_COOKIE) String sessionId) throws GenericException {
        try {
            SociedadAnonimaDTO sociedadAnonimaDTO = SociedadMapper.mapFromJSONObject(new JSONObject(sociedadAnonimaString));
            return this.sociedadService.createSociedad(sociedadAnonimaDTO, file, token, sessionId);
        } catch (JSONException e) {
            logger.warn("Error parsing JSON", e);
            throw new BadRequestException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .message("Error parsing sociedad from body")
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }

    @PutMapping("/api/sociedad/{id}/status")
    public SociedadAnonima updateSociedad(@PathVariable("id") Long id,
                                          @RequestParam("aprobado") boolean aprobado,
                                          @RequestHeader(ROLE) String role,
                                          @RequestHeader(BONITA_TOKEN) String token,
                                          @RequestHeader(SESSION_ID_COOKIE) String sessionId,
                                          @RequestBody CredentialsDTO credentialsDTO) throws GenericException {
        return this.sociedadService.updateSociedadStatus(id, aprobado, credentialsDTO, token, sessionId);
    }

    @GetMapping("/sociedad/{id}")
    public SociedadAnonima getSociedad(@PathVariable("id") Long expedienteId) {
        return this.sociedadService.getSociedad(expedienteId);
    }
}
