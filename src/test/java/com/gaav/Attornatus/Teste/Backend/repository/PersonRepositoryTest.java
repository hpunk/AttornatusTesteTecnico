package com.gaav.Attornatus.Teste.Backend.repository;

import com.gaav.Attornatus.Teste.Backend.base.BaseRepositoryTest;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PersonRepositoryTest extends BaseRepositoryTest {

    @Autowired
    PersonRepository repository;

    @Test
    public void givenAPersonCreationRequestWhenRequestIsExecutedThenEntityCanBeFetchedFromDB(){
        Person person1 = getPerson();

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
        Person person1 = getPerson();

        val saved = repository.save(person1);

        val response = repository.findById(saved.getPersonId());

        log.info("found {}", response);

        Assertions.assertThat(response.isPresent()).isTrue();
        Assertions.assertThat(response.get().getPersonId()).isEqualTo(saved.getPersonId());

    }

    @Test
    public void givenAFindByNameAndBirthDateRequestWhenPersonExistsThenReturnPerson(){
        Person person1 = getPerson();

        val saved = repository.save(person1);

        val response = repository.findAllByNameAndBirthDate(person1.getName(), person1.getBirthDate());

        log.info("found {}", response);

        Assertions.assertThat(response.isEmpty()).isFalse();
        Assertions.assertThat(response.size()).isEqualTo(1);
        Assertions.assertThat(response.get(0).getPersonId()).isEqualTo(saved.getPersonId());
    }
}
