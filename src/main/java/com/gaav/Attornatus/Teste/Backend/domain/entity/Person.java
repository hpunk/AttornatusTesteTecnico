package com.gaav.Attornatus.Teste.Backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "pessoa")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_pessoa")
    private UUID personId;
    @Column(name = "nome", nullable = false)
    @NotNull
    private String name;
    @Column(name = "data_nascimento", nullable = false)
    @NotNull
    private Date birthDate;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "person"
    )
    private List<Address> addresses = new ArrayList<>();
}
