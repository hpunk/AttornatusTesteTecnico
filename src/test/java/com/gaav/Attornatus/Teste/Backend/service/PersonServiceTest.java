package com.gaav.Attornatus.Teste.Backend.service;

import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonRequest;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonAlreadyExistsException;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonNotFoundException;
import com.gaav.Attornatus.Teste.Backend.repository.PersonPagingRepository;
import com.gaav.Attornatus.Teste.Backend.repository.PersonRepository;
import com.gaav.Attornatus.Teste.Backend.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @InjectMocks
    PersonServiceImpl service;


    @Mock
    PersonPagingRepository personPagingRepository;
    @Mock
    PersonRepository personRepository;

    //Create person
    @Test
    public void givenACreatePersonRequestWhenNameAndBirthDateAreNotRegisteredYetThenPersonIsSaved(){
        PersonRequest request = new PersonRequest();
        request.setName("Eduarda");
        request.setBirthDate(LocalDate.of(1999, Month.JANUARY, 30));

        Person requestEntity = request.toEntity();
        Person requestEntityWithId = request.toEntity();
        requestEntityWithId.setPersonId(UUID.randomUUID());

        when(personRepository.save(requestEntity)).thenReturn(requestEntityWithId);
        when(personRepository.findAllByNameAndBirthDate(request.getName(), request.getBirthDate())).thenReturn(new ArrayList<>());

        service.createPerson(request);

        verify(personRepository, times(1)).findAllByNameAndBirthDate(request.getName(),request.getBirthDate());
        verify(personRepository, times(1)).save(requestEntity);
    }

    @Test
    public void givenACreatePersonRequestWhenNameAndBirthDateAlreadyExistThenPersonExceptionIsThrown(){
        PersonRequest request = new PersonRequest();
        request.setName("Eduarda");
        request.setBirthDate(LocalDate.of(1999, Month.JANUARY, 30));

        Person requestEntityWithId = request.toEntity();
        requestEntityWithId.setPersonId(UUID.randomUUID());

        when(personRepository.findAllByNameAndBirthDate(request.getName(), request.getBirthDate())).thenReturn(List.of(requestEntityWithId));

        Assertions.assertThrows(PersonAlreadyExistsException.class, () -> service.createPerson(request));

        verify(personRepository, times(1)).findAllByNameAndBirthDate(request.getName(),request.getBirthDate());
        verify(personRepository, times(0)).save(any());
    }

    //Update person
    @Test
    public void givenAnUpdatePersonRequestWhenPersonDoesExistThenUpdateIsExecuted(){
        final UUID key = UUID.randomUUID();

        Person storedPerson = new Person();
        storedPerson.setName("Eduarda");
        storedPerson.setBirthDate(LocalDate.of(1990, Month.FEBRUARY, 15));
        storedPerson.setPersonId(key);

        PersonRequest request = new PersonRequest();
        request.setName("Eduarda");
        request.setBirthDate(LocalDate.of(1999, Month.JANUARY, 30));
        request.setId(key);

        Person requestEntityWithId = request.toEntity();
        requestEntityWithId.setPersonId(key);

        when(personRepository.save(requestEntityWithId)).thenReturn(requestEntityWithId);
        when(personRepository.findById(key)).thenReturn(Optional.of(storedPerson));

        service.editPerson(request);

        verify(personRepository, times(1)).findById(key);
        verify(personRepository, times(1)).save(requestEntityWithId);
    }

    @Test
    public void givenAnUpdatePersonRequestWhenPersonDoesNotExistThenExceptionIsThrown(){
        final UUID key = UUID.randomUUID();

        Person storedPerson = new Person();
        storedPerson.setName("Eduarda");
        storedPerson.setBirthDate(LocalDate.of(1990, Month.FEBRUARY, 15));
        storedPerson.setPersonId(key);

        PersonRequest request = new PersonRequest();
        request.setName("Eduarda");
        request.setBirthDate(LocalDate.of(1999, Month.JANUARY, 30));
        request.setId(key);

        when(personRepository.findById(key)).thenReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFoundException.class, () -> service.editPerson(request));

        verify(personRepository, times(1)).findById(key);
        verify(personRepository, times(0)).save(any());
    }

    // List people
    @Test
    public void givenAPeopleListRequestWhenFilterIsEmptyThenFetchDataPaginatedByDefaultValues(){
        final int defaultRows = 10;
        final int defaultPage = 0;

        when(personPagingRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        service.listPeople(new PaginatedFilter());

        verify(personPagingRepository, times(1)).findAll(PageRequest.of(defaultPage,defaultRows));
    }

    @Test
    public void givenAPeopleListRequestWhenFilterSpecifiesPaginationParametersThenFetchDataAccordingToThem(){
        final int rows = 4;
        final int page = 1;

        when(personPagingRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        PaginatedFilter filterToUse = new PaginatedFilter();
        filterToUse.setPage(page);
        filterToUse.setRows(rows);

        service.listPeople(filterToUse);

        verify(personPagingRepository, times(1)).findAll(PageRequest.of(page,rows));
    }


}
