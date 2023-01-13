package com.gaav.Attornatus.Teste.Backend.controller.impl;

import com.gaav.Attornatus.Teste.Backend.controller.PersonApi;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;
import com.gaav.Attornatus.Teste.Backend.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PersonController implements PersonApi {
    private final PersonService personService;

    @Override
    public PersonResponse createPerson(PersonRequest personDto) {
        return personService.createPerson(personDto);
    }

    @Override
    public PersonResponse editPerson(PersonRequest personDto) {
        return personService.editPerson(personDto);
    }

    @Override
    public PersonResponse getPerson(UUID personId) {
        return personService.getPerson(personId);
    }

    @Override
    public PaginatedResponse<PersonResponse> listPeople(PaginatedFilter filter) {
        return personService.listPeople(filter);
    }
}
