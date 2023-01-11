package com.gaav.Attornatus.Teste.Backend.controller;

import com.gaav.Attornatus.Teste.Backend.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PersonController /*implements PersonApi*/ {
    private final PersonService personService;
/*
    public PersonDto createPerson(PersonDto personDto){

    }

    public PersonDto editPerson(PersonDto personDto){

    }

    public PersonDto getPerson(BigInteger personCode){

    }

    public List<PersonDto> listPeople(PersonPaginatedFilter filter){

    }

    public EnderecoDto createAddressForPerson(EnderecoDto address){

    }

    public List<EnderecoDto> listPersonAddresses(String personCode){

    }

    public EnderecoDto registerPersonMainAddress(String personCode, Integer addressIndex){

    }*/
}
