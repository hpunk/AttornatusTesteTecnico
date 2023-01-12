package com.gaav.Attornatus.Teste.Backend.service;

import com.gaav.Attornatus.Teste.Backend.repository.AddressPagingRepository;
import com.gaav.Attornatus.Teste.Backend.repository.AddressRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    AddressService addressService;
    @Mock
    AddressPagingRepository addressPagingRepository;
    @Mock
    AddressRepository addressRepository;
}
