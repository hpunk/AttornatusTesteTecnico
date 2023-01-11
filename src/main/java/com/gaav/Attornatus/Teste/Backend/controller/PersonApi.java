package com.gaav.Attornatus.Teste.Backend.controller;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressDto;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface PersonApi {

    PersonDto createPerson(@NotNull PersonDto personDto);

    PersonDto editPerson(@NotNull PersonDto personDto);

    PersonDto getPerson(@NotNull UUID personId);

    List<PersonDto> listPeople(@Valid PaginatedFilter filter);

    AddressDto createAddressForPerson(@NotNull AddressDto addressRequest);

    List<AddressDto> listPersonAddresses(@NotBlank UUID personId);

    AddressDto registerPersonMainAddress(@NotBlank UUID personId, @NotNull UUID addressId);
}
