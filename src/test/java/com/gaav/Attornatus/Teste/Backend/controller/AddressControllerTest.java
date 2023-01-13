package com.gaav.Attornatus.Teste.Backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressPaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.AddressResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.address.MainAddressRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonBaseRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;
import com.gaav.Attornatus.Teste.Backend.service.AddressService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(AddressApi.class)
@Slf4j
public class AddressControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    AddressService addressService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Create address for person
    @Test
    @SneakyThrows
    public void givenACreateAddressRequestWhenRequestIsCorrectlyBuiltThenStatusIsOkIsReturned() {
        val request = new AddressRequest();
        request.setNumber("23");
        request.setCity("Cidadezinha");
        request.setStreet("Rua Grande");
        request.setZip("232-22");
        request.setPersonId(UUID.randomUUID());

        val createdAddress = request.toEntity();
        createdAddress.setAddressId(UUID.randomUUID());

        Mockito.when(addressService.createAddressForPerson(any())).thenReturn(AddressResponse.fromEntity(createdAddress));

        mockMvc.perform(
                        post("/teste/endereco")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void givenACreateAddressRequestWhenRequestIsMissingRequireParametersThenErrorStatusAndMessageAreReturned() {
        val request = new AddressRequest();

        val createdAddress = request.toEntity();
        createdAddress.setAddressId(UUID.randomUUID());

        Mockito.when(addressService.createAddressForPerson(any())).thenReturn(AddressResponse.fromEntity(createdAddress));

        mockMvc.perform(
                        post("/teste/endereco")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

    }
    // List addresses for person

    @Test
    @SneakyThrows
    public void givenAListAddressesRequestWhenRequestContainsPersonIdThenStatusIsOkIsReturned() {

        Mockito.when(addressService.listPersonAddresses(any())).thenReturn(new PaginatedResponse<>());

        mockMvc.perform(
                        get("/teste/endereco")
                                .param("id_pessoa",UUID.randomUUID().toString())
                )
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void givenAListAddressesRequestWhenRequestIsMissingPersonIdThenErrorStatusAndMessagesAreReturned() {
        Mockito.when(addressService.listPersonAddresses(any())).thenReturn(new PaginatedResponse<>());

        mockMvc.perform(
                        get("/teste/endereco")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void givenAListAddressesRequestPaginationParametersAreNotValidThenErrorStatusAndMessagesAreReturned() {
        Mockito.when(addressService.listPersonAddresses(any())).thenReturn(new PaginatedResponse<>());

        mockMvc.perform(
                        get("/teste/endereco")
                                .param("id_pessoa",UUID.randomUUID().toString())
                                .param("pagina","-1")
                                .param("filas", "0")
                )
                .andExpect(status().isBadRequest());
    }
    // Inform main address for person
    @Test
    @SneakyThrows
    public void givenAnInformMainAddressRequestWhenRequestIsEmptyThenErrorStatusAndMessageAreReturned() {
        Mockito.when(addressService.registerPersonMainAddress(any())).thenReturn(new AddressResponse());

        mockMvc.perform(
                        put("/teste/endereco")
                                .content(asJsonString(new MainAddressRequest()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void givenAnInformMainAddressRequestWhenRequestValuesAreIncorrectThenErrorStatusAndMessageAreReturned() {
        Mockito.when(addressService.registerPersonMainAddress(any())).thenReturn(new AddressResponse());

        mockMvc.perform(
                        put("/teste/endereco")
                                .content("{\"id_pessoa\":\"x\",\"id_endereco\":\"x\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void givenAnInformMainAddressRequestWhenRequestRequestIsCorrectlyBuiltThenServiceMethodIsInvoked() {
        Mockito.when(addressService.registerPersonMainAddress(any())).thenReturn(new AddressResponse());

        val request = new MainAddressRequest();
        request.setAddressId(UUID.randomUUID());
        request.setPersonId(UUID.randomUUID());

        mockMvc.perform(
                        put("/teste/endereco")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

}
