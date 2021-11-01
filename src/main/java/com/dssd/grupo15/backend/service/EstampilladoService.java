package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.rest.estampillado.EstampilladoDTO;
import com.dssd.grupo15.backend.dto.rest.estampillado.HashDTO;
import com.dssd.grupo15.backend.dto.rest.request.CredentialsDTO;
import com.dssd.grupo15.backend.model.Expediente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EstampilladoService {

    @Value("${estampillado.url}")
    private String ESTAMPILLADO_URL;
    @Value("${estampillado.jwt.secret}")
    private String SECRET;

    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(EstampilladoService.class);

    @Autowired
    public EstampilladoService() {
        this.restTemplate = new RestTemplate();
    }

    public String estampillar(Expediente expediente, CredentialsDTO credentialsDTO) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("data", this.generateToken(EstampilladoDTO.Builder.anEstampilladoDTO()
                .expediente(expediente.getId())
                .username(credentialsDTO.getUsername())
                .password(credentialsDTO.getPassword()).build()));

        HashDTO hashResponse = restTemplate.postForObject(ESTAMPILLADO_URL,
                new HttpEntity<>(requestBody),
                HashDTO.class);

        return hashResponse.getHash();
    }

    private String generateToken(EstampilladoDTO estampilladoDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return Jwts.builder().setPayload(objectMapper.writeValueAsString(estampilladoDTO))
                    .signWith(SignatureAlgorithm.HS256, SECRET.getBytes(StandardCharsets.UTF_8)).compact();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
