package com.gaav.Attornatus.Teste.Backend.domain.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AddressPK implements Serializable {
    @Column(name = "id")
    private UUID id;
    @Column(name = "id_pessoa")
    private UUID personId;
}
