package com.gaav.Attornatus.Teste.Backend.repository;

import com.gaav.Attornatus.Teste.Backend.base.BaseRepositoryTest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@Slf4j
public class PersonPagingRepositoryTest extends BaseRepositoryTest {
    @Autowired
    PersonRepository repository;
    @Autowired
    PersonPagingRepository pagingRepository;

    @Test
    public void givenAFindAllRequestWhenThereIsPageableFiltersThenReturnPagedData(){
        final int rows = 3;
        val savedPeople = repository.saveAll(generateListOfPerson());

        val pagedResponse1 = pagingRepository.findAll(PageRequest.of(0,rows));
        val pagedResponse2 = pagingRepository.findAll(PageRequest.of(1,rows));

        log.info("first answer {}", pagedResponse1.getContent());
        log.info("second answer {}", pagedResponse2.getContent());

        Assertions.assertThat(pagedResponse1.getNumberOfElements()).isEqualTo(rows);
        Assertions.assertThat(pagedResponse2.getNumberOfElements()).isEqualTo(rows);

        Assertions.assertThat(pagedResponse1.getContent()).isSubsetOf(savedPeople);
        Assertions.assertThat(pagedResponse2.getContent()).isSubsetOf(savedPeople);
    }

}
