package com.gaav.Attornatus.Teste.Backend.repository;

import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;

import org.assertj.core.api.Assertions;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {

    @Autowired
    PersonRepository repository;

    @Test
    public void givenAPersonCreationRequestWhenRequestIsExecutedThenEntityCanBeFetchedFromDB(){
        Person person1 = new Person();
        person1.setName("Carlos");
        person1.setBirthDate(LocalDate.of(2000, Month.JANUARY,24));

        val response = repository.save(person1);

        val saved = repository.findById(response.getPersonId());
        log.info("saved {}", saved);
        Assertions.assertThat(saved.isPresent()).isTrue();
        Assertions.assertThat(saved.get().getPersonId()).isEqualTo(response.getPersonId());
        Assertions.assertThat(saved.get().getName()).isEqualTo(person1.getName());
        Assertions.assertThat(saved.get().getBirthDate()).isEqualTo(person1.getBirthDate());
    }

    @Test
    public void givenAFindPersonByCodeRequestWhenPersonExistsThenReturnPerson(){
        Person person1 = new Person();
        person1.setName("Luiz");
        person1.setBirthDate(LocalDate.of(1990, Month.FEBRUARY,15));

        val saved = repository.save(person1);

        val response = repository.findById(saved.getPersonId());

        log.info("found {}", response);

        Assertions.assertThat(response.isPresent()).isTrue();
        Assertions.assertThat(response.get().getPersonId()).isEqualTo(saved.getPersonId());

    }

    @Test
    public void givenAFindByNameAndBirthDateRequestWhenPersonExistsThenReturnPerson(){
        Person person1 = new Person();
        person1.setName("Marquinhos");
        person1.setBirthDate(LocalDate.of(1978, Month.JULY,24));

        val saved = repository.save(person1);

        val response = repository.findByNameAndBirthDate(person1.getName(), person1.getBirthDate());

        log.info("found {}", response);

        Assertions.assertThat(response.isPresent()).isTrue();
        Assertions.assertThat(response.get().getPersonId()).isEqualTo(saved.getPersonId());
    }
}
