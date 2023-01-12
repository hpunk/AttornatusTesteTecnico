package com.gaav.Attornatus.Teste.Backend.service;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressPaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;

import java.util.UUID;

public interface AddressService {

    AddressResponse createAddressForPerson(AddressRequest addressRequest);

    PaginatedResponse<AddressResponse> listPersonAddresses(AddressPaginatedFilter filter);

    AddressResponse registerPersonMainAddress(UUID personId, UUID addressId);

}
