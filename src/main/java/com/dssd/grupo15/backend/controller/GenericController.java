package com.dssd.grupo15.backend.controller;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.exception.common.GenericException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GenericController {

    protected static final String BONITA_TOKEN = "X-Bonita-API-Token";
    protected static final String ROLE = "ROLE";
    protected static final String SESSION_ID_COOKIE = "JSESSIONID";

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<StatusCodeDTO> handleException(GenericException genericException) {
        return new ResponseEntity<>(genericException.getStatus(), genericException.getStatus().getStatus());
    }
}
