package com.gaav.Attornatus.Teste.Backend.domain.controller.person;

import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PersonDto {
    private UUID id;
    private String name;
    private LocalDate birthDate;

    public static PersonDto fromEntity(Person person){
        PersonDto dto = new PersonDto();
        dto.setId(person.getPersonId());
        dto.setName(person.getName());
        dto.setBirthDate(person.getBirthDate());
        return dto;
    }

    public Person toEntity(){
        Person person = new Person();
        person.setName(this.name);
        person.setPersonId(this.id);
        person.setBirthDate(this.birthDate);
        return person;
    }
}
