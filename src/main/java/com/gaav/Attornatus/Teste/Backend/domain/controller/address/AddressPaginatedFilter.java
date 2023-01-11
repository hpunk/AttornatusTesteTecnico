package com.gaav.Attornatus.Teste.Backend.domain.controller.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import lombok.Data;

@Data
public class AddressPaginatedFilter extends PaginatedFilter {
    @JsonProperty("codigoPessoa")
    private String personId;
}
