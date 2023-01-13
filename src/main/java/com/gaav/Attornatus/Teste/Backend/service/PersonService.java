package com.gaav.Attornatus.Teste.Backend.service;

import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonBaseRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonUpdateRequest;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;

import java.util.UUID;

public interface PersonService {

    PersonResponse createPerson(PersonBaseRequest personDto);

    PersonResponse editPerson(PersonUpdateRequest personDto);

    Person savePersonEntity(Person person);

    PersonResponse getPerson(UUID personId);

    Person getPersonById(UUID personId);

    PaginatedResponse<PersonResponse> listPeople(PaginatedFilter filter);

}
