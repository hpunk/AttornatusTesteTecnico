package com.gaav.Attornatus.Teste.Backend.domain.controller.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressDto {
    @JsonProperty("idPessoa")
    private UUID personId;
    @JsonProperty("principal")
    private Boolean isMain;
    @JsonProperty("logradouro")
    private String street;
    @JsonProperty("cep")
    private String zip;
    @JsonProperty("numero")
    private String number;
    @JsonProperty("cidade")
    private String city;

    public static AddressDto fromEntity(Address entity) {
        AddressDto dto = new AddressDto();
        dto.setZip(entity.getZip());
        dto.setIsMain(entity.getIsMain());
        dto.setStreet(entity.getStreet());
        dto.setNumber(entity.getNumber());
        dto.setCity(entity.getCity());
        return dto;
    }

    public Address toEntity() {
        Address entity = new Address();
        entity.setCity(this.city);
        entity.setStreet(this.street);
        entity.setZip(this.zip);
        entity.setNumber(this.number);
        entity.setIsMain(this.isMain);
        return entity;
    }
}
