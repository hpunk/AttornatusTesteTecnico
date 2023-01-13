package com.gaav.Attornatus.Teste.Backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ResponseStatus(HttpStatus.CONFLICT)
public class PersonAlreadyExistsException extends RuntimeException{
    public PersonAlreadyExistsException(String name, Date birthDate) {
        super(String.format("A pessoa com nome '%s' e data de nascimento %s ja existe.",name, birthDate.toString()));
    }
}
