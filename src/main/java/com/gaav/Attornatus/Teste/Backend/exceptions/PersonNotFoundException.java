package com.gaav.Attornatus.Teste.Backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(UUID id) {
        super(String.format("A pessoa com id %s nao existe",id));
    }
}
