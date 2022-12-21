package io.GuiWEspinola.poc1.controller.exception;

import io.GuiWEspinola.poc1.exception.*;
import io.GuiWEspinola.poc1.exception.dto.ApiErrorsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiErrorsDTO> customerNotFoundExceptionHandler(CustomerNotFoundException exception,
                                                                         HttpServletRequest request) {
        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .httpStatus(NOT_FOUND.value()).build();

        return ResponseEntity.status(NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ApiErrorsDTO> addressNotFoundExceptionHandler(AddressNotFoundException exception,
                                                                        HttpServletRequest request) {
        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .httpStatus(NO_CONTENT.value()).build();

        return ResponseEntity.status(NO_CONTENT).body(errorDTO);
    }

    @ExceptionHandler(ZipCodeNotFoundException.class)
    public ResponseEntity<ApiErrorsDTO> invalidZipCodeHandler(ZipCodeNotFoundException exception,
                                                              HttpServletRequest request) {
        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .httpStatus(BAD_REQUEST.value()).build();

        return ResponseEntity.status(BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiErrorsDTO> invalidZipCodeHandler(HttpClientErrorException exception,
                                                              HttpServletRequest request) {
        var errorDTO = ApiErrorsDTO.builder()
                .message("Invalid Zip Code.")
                .httpStatus(exception.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(exception.getStatusCode()).body(errorDTO);
    }

    @ExceptionHandler(AddressMaxLimitException.class)
    public ResponseEntity<ApiErrorsDTO> addressMaxLimitExceeded(AddressMaxLimitException exception,
                                                                HttpServletRequest request) {
        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .httpStatus(exception.getHttpStatus().value()).build();

        return ResponseEntity.status(CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(MainAddressDeleteException.class)
    public ResponseEntity<ApiErrorsDTO> mainAddressDeleteExceptionHandler(MainAddressDeleteException exception,
                                                                          HttpServletRequest request) {
        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .httpStatus(CONFLICT.value()).build();

        return ResponseEntity.status(CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ApiErrorsDTO>> validationHandler(ConstraintViolationException exception,
                                                                HttpServletRequest request) {
        List<ApiErrorsDTO> errorsDTOList = new ArrayList<>();
        var constraintViolations = exception.getConstraintViolations();

        constraintViolations.forEach(e -> {
            var errorDTO = ApiErrorsDTO.builder()
                    .message(e.getMessage())
                    .httpStatus(BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now()).build();

            errorsDTOList.add(errorDTO);
        });

        return ResponseEntity.status(BAD_REQUEST).body(errorsDTOList);
    }

    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<ApiErrorsDTO> existingEmailHandler(ExistingEmailException exception,
                                                             HttpServletRequest request) {
        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .httpStatus(CONFLICT.value()).build();

        return ResponseEntity.status(CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(DocumentInUseException.class)
    public ResponseEntity<ApiErrorsDTO> documentRegisteredHandler(DocumentInUseException exception,
                                                                  HttpServletRequest request) {
        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .httpStatus(CONFLICT.value()).build();

        return ResponseEntity.status(CONFLICT).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ApiErrorsDTO>> methodArgumentExceptionHandler(MethodArgumentNotValidException exception,
                                                                             HttpServletRequest request) {
        List<ApiErrorsDTO> errorsDTOList = new ArrayList<>();
        var fieldErrorList = exception.getBindingResult().getAllErrors();

        fieldErrorList.forEach(e -> {
            var errorDTO = ApiErrorsDTO.builder()
                    .timestamp(LocalDateTime.now())
                    .message(e.getDefaultMessage())
                    .httpStatus(BAD_REQUEST.value())
                    .path(request.getRequestURI()).build();

            errorsDTOList.add(errorDTO);
        });
        return ResponseEntity.status(BAD_REQUEST).body(errorsDTOList);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorsDTO> httpMessageException(HttpMessageNotReadableException exception,
                                                             HttpServletRequest request) {

        var errorDTO = ApiErrorsDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getLocalizedMessage())
                .httpStatus(BAD_REQUEST.value())
                .path(request.getRequestURI()).build();

        return ResponseEntity.status(BAD_REQUEST).body(errorDTO);
    }
}
