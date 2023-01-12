package com.gaav.Attornatus.Teste.Backend.repository;

import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressPagingRepository extends PagingAndSortingRepository<Address, UUID> {
    Page<Address> findAllByPersonPersonId(UUID personId, Pageable pageable);
}
