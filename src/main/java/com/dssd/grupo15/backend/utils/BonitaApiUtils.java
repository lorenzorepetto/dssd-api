package com.dssd.grupo15.backend.utils;

import com.dssd.grupo15.backend.dto.rest.response.LoginResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BonitaApiUtils {

    private static final String BONITA_API_TOKEN = "X-Bonita-API-Token";
    private static final String SESSION_ID_COOKIE = "JSESSIONID";

    public static HttpEntity<?> getEntityWithHeaders(String token, String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, String.format("JSESSIONID=%s; Path=/; HttpOnly; SameSite=Lax", sessionId));
        headers.add(BONITA_API_TOKEN, token);
        return new HttpEntity<>(headers);
    }

    public static HttpEntity<?> getEntityWithHeadersAndBody(String token, String sessionId, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, String.format("JSESSIONID=%s; Path=/; HttpOnly; SameSite=Lax", sessionId));
        headers.add(BONITA_API_TOKEN, token);
        return new HttpEntity<>(body, headers);
    }

    public static LoginResponseDTO getTokenDTOFromCookies(ResponseEntity<String> response) {
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        return new LoginResponseDTO(getCookie(BONITA_API_TOKEN, cookies), getCookie(SESSION_ID_COOKIE, cookies));
    }

    public static String getCookie(String cookie, List<String> cookies) {
        return cookies.stream()
                .filter(c -> c.contains(cookie))
                .map(c -> c.substring(c.indexOf("=") + 1, c.indexOf(";")))
                .findFirst()
                .orElse("");
    }
}
