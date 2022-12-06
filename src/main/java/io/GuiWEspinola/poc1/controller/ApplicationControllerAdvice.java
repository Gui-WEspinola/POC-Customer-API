package io.GuiWEspinola.poc1.controller;

import io.GuiWEspinola.poc1.exception.AddressNotFoundException;
import io.GuiWEspinola.poc1.exception.CustomerNotFoundException;
import io.GuiWEspinola.poc1.exception.dto.ApiErrorsDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiErrorsDTO> customerNotFound(CustomerNotFoundException exception,
                                                         HttpServletRequest httpServletRequest) {
        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .httpStatus(NOT_FOUND.value()).build();

        return ResponseEntity.status(NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ApiErrorsDTO> addressNotFound(AddressNotFoundException exception,
                                                        HttpServletRequest httpServletRequest) {
        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(httpServletRequest.getRequestURI())
                .httpStatus(NOT_FOUND.value()).build();

        return ResponseEntity.status(NOT_FOUND).body(errorDTO);
    }
}
