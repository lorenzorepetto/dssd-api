package com.dssd.grupo15.backend.dto.rest.response;

public class LoginResponseDTO {
    private String token;
    private String sessionId;
    private String role;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String sessionId) {
        this.token = token;
        this.sessionId = sessionId;
    }

    public LoginResponseDTO(String token, String sessionId, String role) {
        this.token = token;
        this.sessionId = sessionId;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
