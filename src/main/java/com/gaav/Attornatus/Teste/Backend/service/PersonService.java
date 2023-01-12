package com.gaav.Attornatus.Teste.Backend.service;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressPaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    PersonResponse createPerson(PersonRequest personDto);

    PersonResponse editPerson(PersonRequest personDto);

    PersonResponse getPerson(UUID personId);

    PaginatedResponse<PersonResponse> listPeople(PaginatedFilter filter);

    AddressResponse createAddressForPerson(AddressRequest addressRequest);

    PaginatedResponse<AddressResponse> listPersonAddresses(AddressPaginatedFilter filter);

    AddressResponse registerPersonMainAddress(UUID personId, UUID addressId);
}
