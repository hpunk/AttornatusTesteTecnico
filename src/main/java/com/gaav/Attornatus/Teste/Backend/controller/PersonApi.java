package com.gaav.Attornatus.Teste.Backend.controller;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface PersonApi {

    PersonRequest createPerson(@NotNull PersonRequest personDto);

    PersonRequest editPerson(@NotNull PersonRequest personDto);

    PersonRequest getPerson(@NotNull UUID personId);

    List<PersonRequest> listPeople(@Valid PaginatedFilter filter);

    AddressResponse createAddressForPerson(@NotNull AddressResponse addressRequest);

    List<AddressResponse> listPersonAddresses(@NotBlank UUID personId);

    AddressResponse registerPersonMainAddress(@NotBlank UUID personId, @NotNull UUID addressId);
}
