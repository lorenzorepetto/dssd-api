package com.dssd.grupo15.backend.dto.rest.response;

public class TokenDTO {
    private String token;
    private String sessionId;

    public TokenDTO() {
    }

    public TokenDTO(String token, String sessionId) {
        this.token = token;
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
