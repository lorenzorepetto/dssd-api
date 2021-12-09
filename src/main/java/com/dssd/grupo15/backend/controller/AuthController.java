package com.dssd.grupo15.backend.controller;

import com.dssd.grupo15.backend.dto.rest.request.CredentialsDTO;
import com.dssd.grupo15.backend.dto.rest.response.LoginResponseDTO;
import com.dssd.grupo15.backend.exception.InvalidCredentialsException;
import com.dssd.grupo15.backend.service.BonitaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController extends GenericController {

    private final BonitaApiService bonitaApiService;

    @Autowired
    public AuthController(BonitaApiService bonitaApiService) {
        this.bonitaApiService = bonitaApiService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody CredentialsDTO credentialsDTO) throws InvalidCredentialsException {
        return this.bonitaApiService.login(credentialsDTO);
    }

    @PostMapping("/api/logout")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> logout(@RequestHeader(BONITA_TOKEN) String token,
                                      @RequestHeader(SESSION_ID_COOKIE) String sessionId,
                                      @RequestHeader(ROLE) String role) throws InvalidCredentialsException {
        return this.bonitaApiService.logout(token, sessionId);
    }
}
