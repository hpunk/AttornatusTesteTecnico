package com.gaav.Attornatus.Teste.Backend.domain.controller.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PersonUpdateRequest extends PersonBaseRequest{
    @NotNull(message = "O id da pessoa tem que ser informado")
    @JsonProperty("id_pessoa")
    private UUID id;

    @Override
    public Person toEntity(){
        Person person = new Person();
        person.setName(this.getName());
        person.setPersonId(this.getId());
        person.setBirthDate(this.getBirthDate());
        return person;
    }
}
