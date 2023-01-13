package com.gaav.Attornatus.Teste.Backend.domain.controller.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaav.Attornatus.Teste.Backend.domain.entity.Address;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressResponse {
    @JsonProperty("id_pessoa")
    private UUID personId;
    @JsonProperty("id_endereco")
    private UUID addressId;
    @JsonProperty("e_principal")
    private Boolean isMain;
    @JsonProperty("logradouro")
    private String street;
    @JsonProperty("cep")
    private String zip;
    @JsonProperty("numero")
    private String number;
    @JsonProperty("cidade")
    private String city;

    public static AddressResponse fromEntity(Address entity) {
        AddressResponse dto = new AddressResponse();
        dto.setAddressId(entity.getAddressId());
        dto.setZip(entity.getZip());
        dto.setIsMain(entity.getIsMain());
        dto.setStreet(entity.getStreet());
        dto.setNumber(entity.getNumber());
        dto.setCity(entity.getCity());
        return dto;
    }


}
