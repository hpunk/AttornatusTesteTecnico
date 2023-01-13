package com.gaav.Attornatus.Teste.Backend.controller.impl;

import com.gaav.Attornatus.Teste.Backend.controller.PersonApi;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonBaseRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonUpdateRequest;
import com.gaav.Attornatus.Teste.Backend.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonController implements PersonApi {
    private final PersonService personService;

    @Override
    public PersonResponse createPerson(PersonBaseRequest personDto) {
        return personService.createPerson(personDto);
    }

    @Override
    public PersonResponse editPerson(PersonUpdateRequest personDto) {
        return personService.editPerson(personDto);
    }

    @Override
    public PersonResponse getPerson(UUID personId) {
        return personService.getPerson(personId);
    }

    @Override
    public PaginatedResponse<PersonResponse> listPeople(PaginatedFilter filter) {
        log.info("el filter {}",filter);
        return personService.listPeople(filter);
    }
}
