package com.dssd.grupo15.backend.exception;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.exception.common.GenericException;

public class AlreadyExistsException extends GenericException {
    public AlreadyExistsException(StatusCodeDTO status) {
        super(status);
    }
}
