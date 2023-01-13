package com.gaav.Attornatus.Teste.Backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonBaseRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonUpdateRequest;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import com.gaav.Attornatus.Teste.Backend.service.PersonService;
import lombok.SneakyThrows;
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
@WebMvcTest(PersonApi.class)
public class PersonControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PersonService personService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //create person
    @Test
    @SneakyThrows
    public void givenACreatePersonRequestWhenRequestHasEmptyFieldsThenErrorStatusAndMessageAreReturned() {
        mockMvc.perform(
                post("/teste/pessoa")
                        .content(asJsonString(new PersonBaseRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    public void givenACreatePersonRequestWhenRequestIsCorrectlyBuiltThenRequestIsSentToService() {
        val request = new PersonBaseRequest();
        request.setName("Carlos");
        request.setBirthDate(Date.valueOf(LocalDate.of(2010, Month.JANUARY,23)));

        val storedPerson = request.toEntity();
        storedPerson.setPersonId(UUID.randomUUID());

        Mockito.when(personService.createPerson(any())).thenReturn(PersonResponse.fromEntity(storedPerson));

        mockMvc.perform(
                        post("/teste/pessoa")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos"))
                .andExpect(jsonPath("$.data_nascimento").value(request.getBirthDate().toString()));
    }

    //get person
    @Test
    @SneakyThrows
    public void givenAGetPersonRequestWhenPersonExistThenReturnData() {
        val storedPerson = new Person();
        storedPerson.setPersonId(UUID.randomUUID());
        storedPerson.setName("Carlos");
        storedPerson.setBirthDate(Date.valueOf(LocalDate.of(2010, Month.JANUARY,23)));

        Mockito.when(personService.getPerson(any())).thenReturn(PersonResponse.fromEntity(storedPerson));

        mockMvc.perform(
                        get(String.format("/teste/pessoa/%s",storedPerson.getPersonId()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos"))
                .andExpect(jsonPath("$.data_nascimento").value(storedPerson.getBirthDate().toString()));
    }
    @Test
    @SneakyThrows
    public void givenAGetPersonRequestWhenParameterDataTypeDoesNotMatchRequiredTypeThenErrorStatusIsReturned() {
        mockMvc.perform(
                        get(String.format("/teste/pessoa/x"))
                )
                .andExpect(status().isBadRequest());
    }


    //list person

    @Test
    @SneakyThrows
    public void givenAGetListOfPersonRequestWhenNoFilterIsSpecifiedThenOkStatusIsReturned() {

        Mockito.when(personService.listPeople(any())).thenReturn(new PaginatedResponse<PersonResponse>());

        mockMvc.perform(
                        get("/teste/pessoa")
                )
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void givenAGetListOfPersonRequestWhenFilterHasInvalidParametersThenErrorStatusIsReturned() {

        mockMvc.perform(
                        get("/teste/pessoa")
                                .param("filas", "3")
                                .param("pagina", "-1")
                )
                .andExpect(status().isBadRequest());
    }

    //edit person

    @Test
    @SneakyThrows
    public void givenAnUpdatePersonRequestWhenRequestIsCorrectlyBuiltThenRequestIsSentToService() {
        val request = new PersonUpdateRequest();
        request.setId(UUID.randomUUID());
        request.setName("Carlos");
        request.setBirthDate(Date.valueOf(LocalDate.of(2010, Month.JANUARY,23)));

        val modified = request.toEntity();


        Mockito.when(personService.editPerson(any())).thenReturn(PersonResponse.fromEntity(modified));

        mockMvc.perform(
                        put("/teste/pessoa")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos"))
                .andExpect(jsonPath("$.data_nascimento").value(request.getBirthDate().toString()));
    }

    @Test
    @SneakyThrows
    public void givenAnUpdatePersonRequestWhenRequestIsNotCompleteThenErrorStatusAndMessageAreReturned() {
        val request = new PersonUpdateRequest();
        request.setName("Carlos");
        request.setBirthDate(Date.valueOf(LocalDate.of(2010, Month.JANUARY,23)));

        mockMvc.perform(
                        put("/teste/pessoa")
                                .content(asJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}
