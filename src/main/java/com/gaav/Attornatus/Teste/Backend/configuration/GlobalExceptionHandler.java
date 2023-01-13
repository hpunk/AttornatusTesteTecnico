package com.gaav.Attornatus.Teste.Backend.configuration;

import com.gaav.Attornatus.Teste.Backend.domain.controller.exception.ErrorResponse;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonAlreadyExistsException;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.BindException;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse manageBindException(BindException ex) {
        log.info("el message {}", ex.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()));
        String messages = ex.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()).toString();
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), messages);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse manageMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                String.format(
                        "O parametro '%s' so aceita valores de tipo %s"
                        , ex.getName()
                        , ex.getRequiredType().getName()
                )
        );
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse managePersonAlreadyExistsException(PersonAlreadyExistsException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse managePersonNotFoundException(PersonNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
