package com.dssd.grupo15.backend.controller;

import com.dssd.grupo15.backend.dto.common.StatusCodeDTO;
import com.dssd.grupo15.backend.exception.common.GenericException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenericController {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<StatusCodeDTO> handleException(GenericException genericException) {
        return new ResponseEntity<>(genericException.getStatus(), genericException.getStatus().getStatus());
    }
}
