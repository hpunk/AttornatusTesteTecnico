package com.gaav.Attornatus.Teste.Backend.service.impl;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressPaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import com.gaav.Attornatus.Teste.Backend.repository.AddressPagingRepository;
import com.gaav.Attornatus.Teste.Backend.repository.AddressRepository;
import com.gaav.Attornatus.Teste.Backend.service.AddressService;
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
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressPagingRepository addressPagingRepository;
    private final PersonService personService;

    @Override
    public AddressResponse createAddressForPerson(AddressRequest address){
        val person = personService.getPersonById(address.getPersonId());

        val saved = saveAddress(address, person);

        AddressResponse response = AddressResponse.fromEntity(saved);
        response.setPersonId(person.getPersonId());

        return response;
    }

    @Override
    public PaginatedResponse<AddressResponse> listPersonAddresses(AddressPaginatedFilter filter){
        val person = personService.getPersonById(filter.getPersonId());
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
        val person = personService.getPersonById(personId);
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

    private Address saveAddress(AddressRequest address, Person person){
        val addressEntity = address.toEntity();
        addressEntity.setIsMain(false);

        person.getAddresses().add(addressEntity);

        personService.savePersonEntity(person);

        return addressEntity;
    }

    private Address getMainAddress(List<Address> addresses) {
        val main = addresses.stream().filter(address -> address.getIsMain()).findFirst();
        return main.get();
    }

}
