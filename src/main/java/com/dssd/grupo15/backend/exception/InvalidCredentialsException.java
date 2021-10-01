package com.dssd.grupo15.backend.exception;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.exception.common.GenericException;

public class InvalidCredentialsException extends GenericException {
    public InvalidCredentialsException(StatusCodeDTO status) {
        super(status);
    }
}
