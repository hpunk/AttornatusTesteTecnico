package com.gaav.Attornatus.Teste.Backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(UUID addressId) {
        super(String.format("Nao existe endereco com id %s", addressId));
    }
}
