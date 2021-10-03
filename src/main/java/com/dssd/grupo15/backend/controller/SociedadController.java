package com.dssd.grupo15.backend.controller;

import com.dssd.grupo15.backend.dto.rest.request.SociedadAnonimaDTO;
import com.dssd.grupo15.backend.exception.AlreadyExistsException;
import com.dssd.grupo15.backend.service.SociedadService;
import com.dssd.grupo15.backend.utils.SociedadMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SociedadController extends GenericController {

    private final SociedadService sociedadService;
    private static final String BONITA_TOKEN = "X-Bonita-API-Token";
    private static final String SESSION_ID_COOKIE = "JSESSIONID";

    @Autowired
    public SociedadController(SociedadService sociedadService) {
        this.sociedadService = sociedadService;
    }

    @PostMapping("/api/sociedad")
    public Object createSociedad(@RequestParam("estatuto") MultipartFile file,
                                 @RequestParam("sociedad_anonima") String sociedadAnonimaString,
                                 @RequestHeader(BONITA_TOKEN) String token,
                                 @RequestHeader(SESSION_ID_COOKIE) String sessionId) throws JSONException, AlreadyExistsException {

        SociedadAnonimaDTO sociedadAnonimaDTO = SociedadMapper.mapFromJSONObject(new JSONObject(sociedadAnonimaString));
        return this.sociedadService.createSociedad(sociedadAnonimaDTO, file, token, sessionId);
    }


}
