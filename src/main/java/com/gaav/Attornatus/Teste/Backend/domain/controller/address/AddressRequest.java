package com.gaav.Attornatus.Teste.Backend.domain.controller.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressRequest {
    @JsonProperty("id_pessoa")
    private UUID personId;
    @JsonProperty("e_principal")
    private Boolean isMain;
    @JsonProperty("logradouro")
    @NotBlank(message = "o endereco precisa ter um 'logradouro'")
    private String street;
    @JsonProperty("cep")
    @NotBlank(message = "o endereco precisa ter um 'cep'")
    private String zip;
    @JsonProperty("numero")
    @NotBlank(message = "o endereco precisa ter um 'numero'")
    private String number;
    @JsonProperty("cidade")
    @NotBlank(message = "o endereco precisa ter uma 'cidade'")
    private String city;

    public Address toEntity() {
        Address entity = new Address();
        entity.setCity(this.getCity());
        entity.setStreet(this.getStreet());
        entity.setZip(this.getZip());
        entity.setNumber(this.getNumber());
        entity.setIsMain(this.getIsMain());
        return entity;
    }
}
