package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.dto.rest.request.CredentialsDTO;
import com.dssd.grupo15.backend.dto.rest.response.TokenDTO;
import com.dssd.grupo15.backend.exception.InvalidCredentialsException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthService {

    private static final String LOGIN_URL = "http://localhost:8080/bonita/loginservice";
    private static final String TOKEN_COOKIE = "X-Bonita-API-Token";
    private final RestTemplate restTemplate;

    public AuthService() {
        this.restTemplate = new RestTemplate();
    }

    public TokenDTO login(CredentialsDTO credentialsDTO) throws InvalidCredentialsException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", credentialsDTO.getUsername());
        map.add("password", credentialsDTO.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(LOGIN_URL, request , String.class);
            return new TokenDTO(this.getTokenFromCookies(response));
        } catch (HttpClientErrorException e) {
            throw new InvalidCredentialsException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Bad credentials!")
                    .build());
        }

    }

    private String getTokenFromCookies(ResponseEntity<String> response) {
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        return cookies.stream()
                .filter(cookie -> cookie.contains(TOKEN_COOKIE))
                .map(cookie -> cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";")))
                .findFirst()
                .orElse(null);
    }
}
