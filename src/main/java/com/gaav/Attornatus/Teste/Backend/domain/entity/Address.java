package com.gaav.Attornatus.Teste.Backend.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "endereco")
@IdClass(AddressPK.class)
public class Address {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Id
    @Column(name = "id_pessoa")
    private UUID personId;
    @Column(name = "e_principal")
    private Boolean isMain;
    @Column(name = "logradouro", nullable = false)
    private String street;
    @Column(name = "cep", nullable = false)
    private String zip;
    @Column(name = "numero", nullable = false)
    private String number;
    @Column(name = "cidade", nullable = false)
    private String city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = false)
    private Person person;
}
