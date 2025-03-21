package com.github.ariadineamaral.ms_pagamento.controller.handlers;

import com.github.ariadineamaral.ms_pagamento.controller.handlers.dto.CustomErrorDTO;
import com.github.ariadineamaral.ms_pagamento.controller.handlers.dto.ValidationErrorDTO;
import com.github.ariadineamaral.ms_pagamento.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorDTO> handleResourceNotFond(ResourceNotFoundException e,
                                                                HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorDTO err = new CustomErrorDTO(Instant.now(), status.value(),
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDTO> methodArgumentNotValidtion(MethodArgumentNotValidException e,
                                                                     HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorDTO err = new ValidationErrorDTO(Instant.now(), status.value(),
                "Dados Inválidos", request.getRequestURI());

        for(FieldError fieldError : e.getBindingResult().getFieldErrors()){
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

//    @ExceptionHandler(DataBaseException.class)
//    public ResponseEntity<CustomErrorDTO> handleDatabase(DataBaseException e, HttpServletRequest request) {
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//
//        CustomErrorDTO err = new CustomErrorDTO(Instant.now(), status.value(),
//                e.getMessage(), request.getRequestURI());
//
//        return ResponseEntity.status(status).body(err);
//    }

}
