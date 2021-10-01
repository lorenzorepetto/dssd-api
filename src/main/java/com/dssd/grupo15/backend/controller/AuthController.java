package com.dssd.grupo15.backend.controller;

import com.dssd.grupo15.backend.dto.rest.Quote;
import com.dssd.grupo15.backend.dto.rest.request.CredentialsDTO;
import com.dssd.grupo15.backend.dto.rest.response.TokenDTO;
import com.dssd.grupo15.backend.exception.InvalidCredentialsException;
import com.dssd.grupo15.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class AuthController extends GenericController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public List<Quote> testRest() {
        RestTemplate restTemplate = new RestTemplate();
        List<Quote> quotes = restTemplate.getForObject("https://quoters.apps.pcfone.io/api", List.class);
        return quotes;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDTO login(@RequestBody CredentialsDTO credentialsDTO) throws InvalidCredentialsException {
        return this.authService.login(credentialsDTO);
    }
}
