package com.gaav.Attornatus.Teste.Backend.domain.controller.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MainAddressRequest {
    @JsonProperty("id_pessoa")
    @NotNull(message = "O id da pessoa deve ser indicado")
    private UUID personId;
    @JsonProperty("id_endereco")
    @NotNull(message = "O id do endereco deve ser indicado")
    private UUID addressId;
}
