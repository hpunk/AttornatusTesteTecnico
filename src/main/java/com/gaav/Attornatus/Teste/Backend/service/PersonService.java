package com.gaav.Attornatus.Teste.Backend.service;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressDto;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonDto;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    PersonDto createPerson(PersonDto personDto);

    PersonDto editPerson(PersonDto personDto);

    PersonDto getPerson(UUID personId);

    PaginatedResponse<PersonDto> listPeople(PaginatedFilter filter);

    AddressDto createAddressForPerson(AddressDto addressRequest);

    List<AddressDto> listPersonAddresses(UUID personId);

    AddressDto registerPersonMainAddress(UUID personId, UUID addressId);
}
