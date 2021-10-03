package com.dssd.grupo15.backend.service;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.dto.rest.bonita.InitProcessDTO;
import com.dssd.grupo15.backend.dto.rest.bonita.InitProcessResponseDTO;
import com.dssd.grupo15.backend.dto.rest.bonita.ProcessDefinitionInfoDTO;
import com.dssd.grupo15.backend.dto.rest.request.CredentialsDTO;
import com.dssd.grupo15.backend.dto.rest.response.TokenDTO;
import com.dssd.grupo15.backend.exception.InvalidCredentialsException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class BonitaApiService {

    private static final String LOGIN_URL = "http://localhost:8080/bonita/loginservice";
    private static final String PROCESS_DEFINITION_INFO_URL = "http://localhost:8080/bonita/API/bpm/process";
    private static final String INIT_PROCESS_URL = "http://localhost:8080/bonita/API/bpm/case";
    private static final String BONITA_API_TOKEN = "X-Bonita-API-Token";
    private static final String SESSION_ID_COOKIE = "JSESSIONID";

    private final RestTemplate restTemplate;

    public BonitaApiService() {
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
            return this.getTokenDTOFromCookies(response);
        } catch (HttpClientErrorException e) {
            throw new InvalidCredentialsException(StatusCodeDTO.Builder.aStatusCodeDTO()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Bad credentials!")
                    .build());
        }

    }

    public String initBonitaProcess(String processName, String token, String sessionId) {
        ProcessDefinitionInfoDTO processInfo = this.getProcessInfo(processName, sessionId);
        try {
            HttpEntity<InitProcessResponseDTO> res = restTemplate.exchange(
                    INIT_PROCESS_URL,
                    HttpMethod.POST,
                    this.getEntityWithHeadersAndBody(token, sessionId, new InitProcessDTO(processInfo.getId(), new ArrayList<>())),
                    new ParameterizedTypeReference<>(){});
            return res.getBody().getId();
        } catch (Exception e) {
            // TODO: hacer catch
            System.out.println("aca");
            return null;
        }
    }

    private ProcessDefinitionInfoDTO getProcessInfo(String processName, String sessionId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PROCESS_DEFINITION_INFO_URL)
                .queryParam("s", processName);

        try {
            HttpEntity<List<ProcessDefinitionInfoDTO>> res = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    this.getEntityWithHeaders("", sessionId),
                    new ParameterizedTypeReference<>(){});
            return res.getBody().get(0);
        } catch (Exception e) {
            // TODO: hacer catch
            System.out.println("aca");
            return null;
        }
    }

    private HttpEntity<?> getEntityWithHeaders(String token, String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, String.format("JSESSIONID=%s; Path=/; HttpOnly; SameSite=Lax", sessionId));
        headers.add(BONITA_API_TOKEN, token);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<?> getEntityWithHeadersAndBody(String token, String sessionId, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, String.format("JSESSIONID=%s; Path=/; HttpOnly; SameSite=Lax", sessionId));
        headers.add(BONITA_API_TOKEN, token);
        return new HttpEntity<>(body, headers);
    }

    private TokenDTO getTokenDTOFromCookies(ResponseEntity<String> response) {
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        return new TokenDTO(getCookie(BONITA_API_TOKEN, cookies), getCookie(SESSION_ID_COOKIE, cookies));
    }

    private String getCookie(String cookie, List<String> cookies) {
        return cookies.stream()
                .filter(c -> c.contains(cookie))
                .map(c -> c.substring(c.indexOf("=") + 1, c.indexOf(";")))
                .findFirst()
                .orElse("");
    }

}
