package com.gaav.Attornatus.Teste.Backend.domain.controller.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class PersonBaseRequest {
    @JsonProperty("nome")
    @NotBlank(message = "O nome da pessoa deve ser informado")
    private String name;
    @JsonProperty("data_nascimento")
    @NotNull(message = "A data de nascimento deve ser informada")
    private Date birthDate;
    public Person toEntity(){
        Person person = new Person();
        person.setName(this.getName());
        person.setBirthDate(this.getBirthDate());
        return person;
    }
}
