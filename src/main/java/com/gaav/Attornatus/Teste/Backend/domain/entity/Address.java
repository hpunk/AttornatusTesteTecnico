package com.gaav.Attornatus.Teste.Backend.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@Entity
@Table(name = "endereco")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_endereco")
    private UUID addressId;
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "personId")
    @ToString.Exclude
    private Person person;
}
