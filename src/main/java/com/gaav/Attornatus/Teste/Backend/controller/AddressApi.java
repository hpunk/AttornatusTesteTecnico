package com.gaav.Attornatus.Teste.Backend.controller;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressPaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.MainAddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/teste/endereco")
public interface AddressApi {

    @PostMapping
    AddressResponse createAddressForPerson(@RequestBody @Valid AddressRequest addressRequest);

    @GetMapping
    PaginatedResponse<AddressResponse> listPersonAddresses(@Valid AddressPaginatedFilter filter);

    @PutMapping
    AddressResponse registerPersonMainAddress(@RequestBody @Valid MainAddressRequest mainAddressRequest);
}
