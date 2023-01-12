package com.gaav.Attornatus.Teste.Backend.service;

import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressPaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import com.gaav.Attornatus.Teste.Backend.exceptions.PersonNotFoundException;
import com.gaav.Attornatus.Teste.Backend.repository.AddressPagingRepository;
import com.gaav.Attornatus.Teste.Backend.repository.AddressRepository;
import com.gaav.Attornatus.Teste.Backend.service.impl.AddressServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    AddressServiceImpl addressService;
    @Mock
    AddressPagingRepository addressPagingRepository;
    @Mock
    AddressRepository addressRepository;
    @Mock
    PersonService personService;

    // Create address for person
    @Test
    public void givenARegisterAddressForPersonWhenPersonDoesNotExistThenExceptionIsThrownAndNoAddressIsStored() {
        final UUID personId = UUID.randomUUID();

        val request = new AddressRequest();
        request.setPersonId(personId);

        when(personService.getPersonById(personId)).thenThrow(PersonNotFoundException.class);
        Assertions.assertThrows(PersonNotFoundException.class, () -> addressService.createAddressForPerson(request));

        verify(personService, times(0)).savePersonEntity(any());
    }

    @Test
    public void givenARegisterAddressForPersonWhenPersonExistsThenAddressStorageIsRequested() {
        final UUID personId = UUID.randomUUID();

        val request = new AddressRequest();
        request.setPersonId(personId);
        request.setCity("Cidade");
        request.setZip("343");
        request.setNumber("1");
        request.setStreet("Rua Grande");

        Person storedPerson = instantiatePerson(personId);

        Person personWithAddress = storedPerson;
        personWithAddress.setAddresses(List.of(request.toEntity()));

        when(personService.getPersonById(personId)).thenReturn(storedPerson);
        when(personService.savePersonEntity(personWithAddress)).thenReturn(storedPerson);

        addressService.createAddressForPerson(request);

        verify(personService, times(1)).savePersonEntity(personWithAddress);
        verify(personService, times(1)).getPersonById(personId);
    }
    // List person addresses

    @Test
    public void givenAListPersonAddressesRequestWhenPersonDoesNotExistThenExceptionIsThrown() {
       final UUID personId = UUID.randomUUID();

        val filter = new AddressPaginatedFilter();
        filter.setPersonId(personId);

        when(personService.getPersonById(personId)).thenThrow(PersonNotFoundException.class);

        Assertions.assertThrows(PersonNotFoundException.class, () -> addressService.listPersonAddresses(filter));

        verify(addressPagingRepository, times(0)).findAllByPersonPersonId(any(), any());
        verify(personService, times(1)).getPersonById(personId);
    }

    @Test
    public void givenAListPersonAddressesRequestWhenFilterSpecifiesPaginationParametersThenRequestDataUsingThem() {
        final int defaultRows = 10;
        final int defaultPage = 0;
        final UUID personId = UUID.randomUUID();

        val filter = new AddressPaginatedFilter();
        filter.setPersonId(personId);

        Person storedPerson = instantiatePerson(personId);

        when(personService.getPersonById(personId)).thenReturn(storedPerson);
        when(addressPagingRepository.findAllByPersonPersonId(any(), any())).thenReturn(Page.empty());
        addressService.listPersonAddresses(filter);

        verify(addressPagingRepository, times(1)).findAllByPersonPersonId(personId, PageRequest.of(defaultPage, defaultRows));
        verify(personService, times(1)).getPersonById(personId);
    }

    @Test
    public void givenAListAddressesRequestWhenFilterDoesNotSpecifyPaginationParametersThenRequestDataUsingDefaultValues() {
        final UUID personId = UUID.randomUUID();

        val filter = new AddressPaginatedFilter();
        filter.setPersonId(personId);
        filter.setPage(1);
        filter.setRows(5);

        Person storedPerson = instantiatePerson(personId);

        when(personService.getPersonById(personId)).thenReturn(storedPerson);
        when(addressPagingRepository.findAllByPersonPersonId(any(), any())).thenReturn(Page.empty());
        addressService.listPersonAddresses(filter);

        verify(addressPagingRepository, times(1)).findAllByPersonPersonId(personId, PageRequest.of(1, 5));
        verify(personService, times(1)).getPersonById(personId);
    }

    // Register main address for person
    @Test
    public void givenARegisterMainAddressRequestWhenPersonDoesNotExistThenExceptionIsThrown() {
        final UUID personId = UUID.randomUUID();

        when(personService.getPersonById(personId)).thenThrow(PersonNotFoundException.class);

        Assertions.assertThrows(PersonNotFoundException.class, () -> addressService.registerPersonMainAddress(personId, UUID.randomUUID()));

        verify(personService, times(1)).getPersonById(personId);
        verify(addressRepository, times(0)).findAllByPerson(any());
        verify(personService, times(0)).savePersonEntity(any());
    }

    @Test
    public void givenARegisterMainAddressRequestWhenThereIsNoMainAddressThenAddressIsRequestedToBeSavedAsMain() {
        final UUID personId = UUID.randomUUID();
        final UUID addressId = UUID.randomUUID();

        val address = instantiateAddress(addressId);
        address.setIsMain(false);

        val addressMain = instantiateAddress(addressId);
        addressMain.setIsMain(true);

        Person storedPerson = instantiatePerson(personId);

        Person personWithMainAddress = instantiatePerson(personId);
        personWithMainAddress.setAddresses(List.of(addressMain));

        when(personService.getPersonById(personId)).thenReturn(storedPerson);
        when(addressRepository.findAllByPerson(storedPerson)).thenReturn(List.of(address));

        addressService.registerPersonMainAddress(personId, addressId);

        verify(personService, times(1)).getPersonById(personId);
        verify(addressRepository, times(1)).findAllByPerson(any());
        verify(personService, times(1)).savePersonEntity(personWithMainAddress);
    }

    @Test
    public void givenARegisterMainAddressRequestWhenAnotherExistingAddressIsMainThenOldMainAddressIsNotMainInDaoRequest() {
        final UUID personId = UUID.randomUUID();
        final UUID oldMainAddressId = UUID.randomUUID();
        final UUID newMainAddressId = UUID.randomUUID();

        val oldMainAddressBefore = instantiateAddress(oldMainAddressId);
        oldMainAddressBefore.setIsMain(true);

        val newMainAddressBefore = instantiateAddress(newMainAddressId);
        newMainAddressBefore.setIsMain(false);


        val oldMainAddressAfter = instantiateAddress(oldMainAddressId);
        oldMainAddressAfter.setIsMain(false);

        val newMainAddressAfter = instantiateAddress(newMainAddressId);
        newMainAddressAfter.setIsMain(true);


        Person storedPerson = instantiatePerson(personId);

        Person personWithMainAddress = instantiatePerson(personId);
        personWithMainAddress.setAddresses(List.of(oldMainAddressAfter, newMainAddressAfter));

        when(personService.getPersonById(personId)).thenReturn(storedPerson);
        when(addressRepository.findAllByPerson(storedPerson)).thenReturn(List.of(oldMainAddressBefore, newMainAddressBefore));

        addressService.registerPersonMainAddress(personId, newMainAddressId);

        verify(personService, times(1)).getPersonById(personId);
        verify(addressRepository, times(1)).findAllByPerson(any());
        verify(personService, times(1)).savePersonEntity(personWithMainAddress);
    }

    private Person instantiatePerson(UUID id) {
        Person person = new Person();
        person.setPersonId(id);
        person.setName("Xiao");
        person.setBirthDate(LocalDate.now());

        return person;
    }

    private Address instantiateAddress(UUID id) {
        val address = new Address();
        address.setAddressId(id);
        address.setCity("Cidade");
        address.setZip("343");
        address.setNumber("1");
        address.setStreet("Rua Grande");
        address.setIsMain(false);

        return address;
    }
}
