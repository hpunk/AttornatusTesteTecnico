package com.gaav.Attornatus.Teste.Backend.service.impl;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressPaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.MainAddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import com.gaav.Attornatus.Teste.Backend.exceptions.AddressNotFoundException;
import com.gaav.Attornatus.Teste.Backend.repository.AddressPagingRepository;
import com.gaav.Attornatus.Teste.Backend.repository.AddressRepository;
import com.gaav.Attornatus.Teste.Backend.service.AddressService;
import com.gaav.Attornatus.Teste.Backend.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

        return buildPaginatedResponse(pagedData, person);
    }

    @Override
    public AddressResponse registerPersonMainAddress(MainAddressRequest request){
        val person = personService.getPersonById(request.getPersonId());

        if(!addressRepository.existsById(request.getAddressId()))
            throw new AddressNotFoundException(request.getAddressId());

        List<Address> existingAddresses = addressRepository.findAllByPerson(person);

        val toUpdate = getAddressesToUpdate(existingAddresses, request);

        updateIsMainFieldInAddresses(person, toUpdate, request);

        val updatedPerson = personService.savePersonEntity(person);

        val response = AddressResponse.fromEntity(getMainAddress(updatedPerson.getAddresses()));
        response.setPersonId(person.getPersonId());
        return response;
    }

    private void updateIsMainFieldInAddresses(Person person, List<Address> toUpdate, MainAddressRequest request){
        for(int index = 0; index < person.getAddresses().size() ; index++){
            val address = person.getAddresses().get(index);
            if(toUpdate.contains(address))
                person.getAddresses().get(index).setIsMain(address.getAddressId().equals(request.getAddressId()));
        }
    }

    private List<Address> getAddressesToUpdate(List<Address> currentAddresses, MainAddressRequest request) {
        return currentAddresses
                .stream()
                .filter(address ->
                        address.getAddressId().equals(request.getAddressId()) || address.getIsMain()
                ).collect(Collectors.toList());
    }

    private PaginatedResponse<AddressResponse> buildPaginatedResponse (Page<Address> pagedResponse, Person person) {
        PaginatedResponse<AddressResponse> response = new PaginatedResponse<>();
        response.setRows(pagedResponse.getSize());
        response.setPage(pagedResponse.getNumber());
        response.setCount(pagedResponse.getTotalElements());

        response.setData(pagedResponse.getContent()
                .stream()
                .map(address -> {
                    val dto = AddressResponse.fromEntity(address);
                    dto.setPersonId(person.getPersonId());
                    return dto;
                }).collect(Collectors.toList()));
        return response;
    }

    private Address saveAddress(AddressRequest address, Person person){
        val addressEntity = address.toEntity();
        addressEntity.setIsMain(false);
        addressEntity.setPerson(person);

        person.getAddresses().add(addressEntity);

        val updatedPerson = personService.savePersonEntity(person);

        return updatedPerson.getAddresses().get(updatedPerson.getAddresses().size()-1);
    }

    private Address getMainAddress(List<Address> addresses) {
        val main = addresses.stream().filter(address -> address.getIsMain()).findFirst();
        return main.get();
    }

}
