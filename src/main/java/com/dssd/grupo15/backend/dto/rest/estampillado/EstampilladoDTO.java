package com.dssd.grupo15.backend.dto.rest.estampillado;

public class EstampilladoDTO {
    private Long expediente;
    private String username;
    private String password;

    public Long getExpediente() {
        return expediente;
    }

    public void setExpediente(Long expediente) {
        this.expediente = expediente;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "EstampilladoDTO{" +
                "expediente=" + expediente +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static final class Builder {
        private EstampilladoDTO estampilladoDTO;

        private Builder() {
            estampilladoDTO = new EstampilladoDTO();
        }

        public static Builder anEstampilladoDTO() {
            return new Builder();
        }

        public Builder expediente(Long expediente) {
            estampilladoDTO.setExpediente(expediente);
            return this;
        }

        public Builder username(String username) {
            estampilladoDTO.setUsername(username);
            return this;
        }

        public Builder password(String password) {
            estampilladoDTO.setPassword(password);
            return this;
        }

        public EstampilladoDTO build() {
            return estampilladoDTO;
        }
    }
}
