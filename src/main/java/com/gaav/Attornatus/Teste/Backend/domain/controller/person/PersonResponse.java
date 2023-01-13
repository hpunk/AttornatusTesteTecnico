package com.gaav.Attornatus.Teste.Backend.domain.controller.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PersonResponse {
    @JsonProperty("id_pessoa")
    private UUID id;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("data_nascimento")
    private Date birthDate;
    public static PersonResponse fromEntity(Person person){
        PersonResponse response = new PersonResponse();
        response.setId(person.getPersonId());
        response.setName(person.getName());
        response.setBirthDate(person.getBirthDate());
        response.setId(person.getPersonId());
        return response;
    }
}
