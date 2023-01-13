package com.gaav.Attornatus.Teste.Backend.controller.impl;

import com.gaav.Attornatus.Teste.Backend.controller.AddressApi;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressPaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.MainAddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressController implements AddressApi {
    private final AddressService addressService;

    @Override
    public AddressResponse createAddressForPerson(AddressRequest addressRequest) {
        return addressService.createAddressForPerson(addressRequest);
    }

    @Override
    public PaginatedResponse<AddressResponse> listPersonAddresses(AddressPaginatedFilter filter) {
        return addressService.listPersonAddresses(filter);
    }

    @Override
    public AddressResponse registerPersonMainAddress(MainAddressRequest mainAddressRequest) {
        return addressService.registerPersonMainAddress(mainAddressRequest);
    }
}
