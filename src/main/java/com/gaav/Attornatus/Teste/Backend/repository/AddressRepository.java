package com.gaav.Attornatus.Teste.Backend.repository;

import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import com.gaav.Attornatus.Teste.Backend.domain.entity.AddressPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, AddressPK> {
    List<Address> findAllByPersonId(UUID personId);

}