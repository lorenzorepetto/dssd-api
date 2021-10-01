package com.dssd.grupo15.backend.dto.common;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class StatusCodeDTO {
    private HttpStatus status;
    private String message;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusCodeDTO that = (StatusCodeDTO) o;
        return status == that.status && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }

    public static final class Builder {
        private StatusCodeDTO statusCodeDTO;

        private Builder() {
            statusCodeDTO = new StatusCodeDTO();
        }

        public static Builder aStatusCodeDTO() {
            return new Builder();
        }

        public Builder status(HttpStatus status) {
            statusCodeDTO.setStatus(status);
            return this;
        }

        public Builder message(String message) {
            statusCodeDTO.setMessage(message);
            return this;
        }

        public StatusCodeDTO build() {
            return statusCodeDTO;
        }
    }
}
