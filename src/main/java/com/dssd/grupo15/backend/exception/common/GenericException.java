package com.dssd.grupo15.backend.exception.common;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;

public class GenericException extends Exception {
    StatusCodeDTO status;

    public GenericException(StatusCodeDTO status) {
        this.status = status;
    }

    public StatusCodeDTO getStatus() {
        return status;
    }

    public void setStatus(StatusCodeDTO status) {
        this.status = status;
    }
}
