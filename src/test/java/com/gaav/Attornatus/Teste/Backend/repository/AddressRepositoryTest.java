package com.gaav.Attornatus.Teste.Backend.repository;

import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressRepositoryTest {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PersonRepository personRepository;

    @Test
    public void givenAFindAddressByPersonIdRequestWhenAddressExistThenReturnAddress() {
        Person person1 = new Person();
        person1.setName("Carlos");
        person1.setBirthDate(LocalDate.of(2000, Month.JANUARY,24));



        Address newAddress = new Address();

        newAddress.setZip("3451-000");
        newAddress.setCity("Rio Pequeno do Meio");
        newAddress.setNumber("34");
        newAddress.setStreet("Rua Ruan");
        newAddress.setIsMain(false);
        newAddress.setPerson(person1);

        person1.setAddresses(List.of(newAddress));
        val savedPerson = personRepository.save(person1);

        addressRepository.flush();
        val addressByPerson = addressRepository.findAllByPerson(savedPerson);

        log.info("addresses by person id {}",addressByPerson);

        Assertions.assertThat(addressByPerson.size()).isEqualTo(1);
        Assertions.assertThat(addressByPerson.get(0).getAddressId()).isEqualTo(savedPerson.getAddresses().get(0).getAddressId());
    }
}
