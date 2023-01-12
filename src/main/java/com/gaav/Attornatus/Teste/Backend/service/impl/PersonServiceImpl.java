package com.gaav.Attornatus.Teste.Backend.service.impl;

import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonAlreadyExistsException;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonNotFoundException;
import com.gaav.Attornatus.Teste.Backend.repository.PersonPagingRepository;
import com.gaav.Attornatus.Teste.Backend.repository.PersonRepository;
import com.gaav.Attornatus.Teste.Backend.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonPagingRepository personPagingRepository;

    @Override
    public PersonResponse createPerson(PersonRequest personDto){
        val existingPerson = personRepository.findAllByNameAndBirthDate(personDto.getName(), personDto.getBirthDate());

        if(!existingPerson.isEmpty())
            throw new PersonAlreadyExistsException(personDto.getName(), personDto.getBirthDate());

        val response = savePersonEntity(personDto.toEntity());
        return PersonResponse.fromEntity(response);
    }

    @Override
    public PersonResponse editPerson(PersonRequest personDto){
        val existingPerson = getPersonById(personDto.getId());

        val toModify = existingPerson;
        toModify.setBirthDate(personDto.getBirthDate());
        toModify.setName(personDto.getName());

        val edited = savePersonEntity(toModify);
        return PersonResponse.fromEntity(edited);
    }

    @Override
    public PersonResponse getPerson(UUID personId){
        val person = getPersonById(personId);
        return PersonResponse.fromEntity(person);
    }

    @Override
    public PaginatedResponse<PersonResponse> listPeople(PaginatedFilter filter){
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getRows());
        val paginatedPeople = personPagingRepository.findAll(pageable);

        PaginatedResponse<PersonResponse> response = new PaginatedResponse<>();
        response.setData(
                paginatedPeople
                        .getContent()
                        .stream()
                        .map(PersonResponse::fromEntity)
                        .collect(Collectors.toList())
        );
        response.setCount(paginatedPeople.getTotalElements());

        return response;
    }

    @Override
    public Person getPersonById(UUID personId) {
        val person = personRepository.findById(personId);
        if(!person.isPresent())
            throw new PersonNotFoundException(personId);
        return person.get();
    }

    @Override
    public Person savePersonEntity(Person person) {
        return personRepository.save(person);
    }

}
