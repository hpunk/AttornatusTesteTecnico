package com.gaav.Attornatus.Teste.Backend.domain.controller.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressPaginatedFilter extends PaginatedFilter {
    @JsonProperty("id_pessoa")
    @NotBlank
    private UUID personId;
}
