package com.dssd.grupo15.backend.controller;

import com.dssd.grupo15.backend.dto.rest.request.CredentialsDTO;
import com.dssd.grupo15.backend.dto.rest.response.TokenDTO;
import com.dssd.grupo15.backend.exception.InvalidCredentialsException;
import com.dssd.grupo15.backend.service.BonitaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController extends GenericController {

    private final BonitaApiService bonitaApiService;

    @Autowired
    public AuthController(BonitaApiService bonitaApiService) {
        this.bonitaApiService = bonitaApiService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDTO login(@RequestBody CredentialsDTO credentialsDTO) throws InvalidCredentialsException {
        return this.bonitaApiService.login(credentialsDTO);
    }
}
