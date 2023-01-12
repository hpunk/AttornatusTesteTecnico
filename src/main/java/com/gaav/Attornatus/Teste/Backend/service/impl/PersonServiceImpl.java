package com.gaav.Attornatus.Teste.Backend.service.impl;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressPaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonAlreadyExistsException;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonNotFoundException;
import com.gaav.Attornatus.Teste.Backend.repository.AddressPagingRepository;
import com.gaav.Attornatus.Teste.Backend.repository.AddressRepository;
import com.gaav.Attornatus.Teste.Backend.repository.PersonPagingRepository;
import com.gaav.Attornatus.Teste.Backend.repository.PersonRepository;
import com.gaav.Attornatus.Teste.Backend.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final AddressRepository addressRepository;
    private final AddressPagingRepository addressPagingRepository;
    private final PersonRepository personRepository;
    private final PersonPagingRepository personPagingRepository;

    @Override
    public PersonResponse createPerson(PersonRequest personDto){
        val existingPerson = personRepository.findAllByNameAndBirthDate(personDto.getName(), personDto.getBirthDate());

        if(!existingPerson.isEmpty())
            throw new PersonAlreadyExistsException(personDto.getName(), personDto.getBirthDate());

        val response = personRepository.save(personDto.toEntity());
        return PersonResponse.fromEntity(response);
    }

    @Override
    public PersonResponse editPerson(PersonRequest personDto){
        val existingPerson = getPersonById(personDto.getId());

        val toModify = existingPerson;
        toModify.setBirthDate(personDto.getBirthDate());
        toModify.setName(personDto.getName());

        val edited = personRepository.save(toModify);
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
    public AddressResponse createAddressForPerson(AddressRequest address){
        val person = getPersonById(address.getPersonId());

        val saved = saveAddress(address, person);

        AddressResponse response = AddressResponse.fromEntity(saved);
        response.setPersonId(person.getPersonId());

        return response;
    }

    @Override
    public PaginatedResponse<AddressResponse> listPersonAddresses(AddressPaginatedFilter filter){
        val person = getPersonById(filter.getPersonId());
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getRows());
        val pagedData = addressPagingRepository.findAllByPersonPersonId(person.getPersonId(), pageable);
        PaginatedResponse<AddressResponse> response = new PaginatedResponse<>();
        response.setRows(filter.getRows());
        response.setPage(filter.getPage());
        response.setCount(pagedData.getTotalElements());
        response.setData(pagedData.getContent().stream().map(AddressResponse::fromEntity).collect(Collectors.toList()));
        return response;
    }

    @Override
    public AddressResponse registerPersonMainAddress(UUID personId, UUID addressId){
        val person = getPersonById(personId);
        List<Address> existingAddresses = addressRepository.findAllByPerson(person);

        val toUpdate = existingAddresses
                        .stream()
                        .filter(address ->
                            address.getAddressId().equals(addressId) || address.getIsMain()
                        ).map(address -> {
                            address.setIsMain(!address.getIsMain());
                            return address;
                        }).collect(Collectors.toList());

        val response = addressRepository.saveAll(toUpdate);

        return AddressResponse.fromEntity(getMainAddress(response));
    }

    private Address getMainAddress(List<Address> addresses) {
        val main = addresses.stream().filter(address -> address.getIsMain()).findFirst();
        return main.get();
    }

    private Person getPersonById(UUID personId) {
        val person = personRepository.findById(personId);
        if(!person.isPresent())
            throw new PersonNotFoundException(personId);
        return person.get();
    }

    private Address saveAddress(AddressRequest address, Person person){
        val addressEntity = address.toEntity();
        addressEntity.setIsMain(false);

        person.getAddresses().add(addressEntity);

        personRepository.save(person);

        return addressEntity;
    }

}
