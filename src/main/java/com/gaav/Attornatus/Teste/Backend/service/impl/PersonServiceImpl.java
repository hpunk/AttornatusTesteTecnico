package com.gaav.Attornatus.Teste.Backend.service.impl;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressDto;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonDto;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonAlreadyExistsException;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonNotFoundException;
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
    private final PersonRepository personRepository;
    private final PersonPagingRepository personPagingRepository;

    @Override
    public PersonDto createPerson(PersonDto personDto){
        val existingPerson = personRepository.findByNameAndBirthDate(personDto.getName(), personDto.getBirthDate());

        if(existingPerson.isPresent())
            throw new PersonAlreadyExistsException(personDto.getName(), personDto.getBirthDate());

        val response = personRepository.save(personDto.toEntity());
        return PersonDto.fromEntity(response);
    }

    @Override
    public PersonDto editPerson(PersonDto personDto){
        val existingPerson = getPersonById(personDto.getId());

        val toModify = existingPerson;
        toModify.setBirthDate(personDto.getBirthDate());
        toModify.setName(personDto.getName());

        val edited = personRepository.save(toModify);
        return PersonDto.fromEntity(edited);
    }

    @Override
    public PersonDto getPerson(UUID personId){
        val person = getPersonById(personId);
        return PersonDto.fromEntity(person);
    }

    @Override
    public PaginatedResponse<PersonDto> listPeople(PaginatedFilter filter){
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getRows());
        val paginatedPeople = personPagingRepository.findAll(pageable);

        PaginatedResponse<PersonDto> response = new PaginatedResponse<>();
        response.setData(
                paginatedPeople
                        .getContent()
                        .stream()
                        .map(PersonDto::fromEntity)
                        .collect(Collectors.toList())
        );
        response.setCount(paginatedPeople.getTotalElements());

        return response;
    }

    @Override
    public AddressDto createAddressForPerson(AddressDto address){
        val person = getPersonById(address.getPersonId());

        val saved = saveAddress(address, person);

        AddressDto response = AddressDto.fromEntity(saved);
        response.setPersonId(person.getPersonId());

        return response;
    }

    @Override
    public List<AddressDto> listPersonAddresses(UUID personId){
        val person = getPersonById(personId);

        return addressRepository.findAllByPerson(person)
                .stream()
                .map(AddressDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto registerPersonMainAddress(UUID personId, UUID addressId){
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

        return AddressDto.fromEntity(getMainAddress(response));
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

    private Address saveAddress(AddressDto address, Person person){
        val addressEntity = address.toEntity();
        addressEntity.setIsMain(false);

        person.getAddresses().add(addressEntity);

        personRepository.save(person);

        return addressEntity;
    }

}
