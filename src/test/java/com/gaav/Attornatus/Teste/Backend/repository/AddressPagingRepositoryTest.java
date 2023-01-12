package com.gaav.Attornatus.Teste.Backend.repository;

import com.gaav.Attornatus.Teste.Backend.base.BaseRepositoryTest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@Slf4j
public class AddressPagingRepositoryTest extends BaseRepositoryTest {
    @Autowired
    AddressPagingRepository addressPagingRepository;
    @Autowired
    PersonRepository personRepository;

    @Test
    public void givenAFindAddressesByPersonIdRequestWhenThereIsPageableFilterProvidedThenReturnPaginatedData() {
        final int rows = 3;

        val person = getPerson();

        val addresses = generateListOfAddressesForPerson(person);
        person.setAddresses(addresses);

        val savedPerson = personRepository.save(person);

        val pagedAddressesResponse1 = addressPagingRepository
                .findAllByPersonPersonId(savedPerson.getPersonId(), PageRequest.of(0,rows));

        val pagedAddressesResponse2 = addressPagingRepository
                .findAllByPersonPersonId(savedPerson.getPersonId(), PageRequest.of(1,rows));

        log.info("first paged response {}", pagedAddressesResponse1.getContent());
        log.info("second paged response {}", pagedAddressesResponse2.getContent());

        Assertions.assertThat(pagedAddressesResponse1.getNumberOfElements()).isEqualTo(rows);
        Assertions.assertThat(pagedAddressesResponse2.getNumberOfElements()).isEqualTo(rows);

        Assertions.assertThat(pagedAddressesResponse1.getContent()).isSubsetOf(savedPerson.getAddresses());
        Assertions.assertThat(pagedAddressesResponse2.getContent()).isSubsetOf(savedPerson.getAddresses());
    }

}
