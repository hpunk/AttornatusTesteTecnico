package com.gaav.Attornatus.Teste.Backend.repository;

import com.gaav.Attornatus.Teste.Backend.base.BaseRepositoryTest;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Person;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class AddressRepositoryTest extends BaseRepositoryTest {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PersonRepository personRepository;

    @Test
    public void givenAFindAddressByPersonIdRequestWhenAddressExistThenReturnAddress() {
        Person person1 = getPerson();

        Address newAddress = getAddress();

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
