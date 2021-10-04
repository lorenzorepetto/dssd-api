package com.dssd.grupo15.backend.exception;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.exception.common.GenericException;

public class BadRequestException extends GenericException {
    public BadRequestException(StatusCodeDTO status) {
        super(status);
    }
}
