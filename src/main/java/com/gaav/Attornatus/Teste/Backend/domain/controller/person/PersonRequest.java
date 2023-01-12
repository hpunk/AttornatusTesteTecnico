package com.gaav.Attornatus.Teste.Backend.domain.controller.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PersonRequest {
    @JsonProperty("id_pessoa")
    private UUID id;
    @JsonProperty("nome")
    @NotBlank
    private String name;
    @JsonProperty("data_nascimento")
    @NotNull
    private LocalDate birthDate;
    public Person toEntity(){
        Person person = new Person();
        person.setName(this.getName());
        person.setPersonId(this.getId());
        person.setBirthDate(this.getBirthDate());
        return person;
    }
}
