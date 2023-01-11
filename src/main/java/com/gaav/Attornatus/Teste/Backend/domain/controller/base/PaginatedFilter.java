package com.gaav.Attornatus.Teste.Backend.domain.controller.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaginatedFilter {
    @Min(1)
    @Valid
    @JsonProperty("filas")
    private Integer rows = 10;
    @NotNull
    @Valid
    @JsonProperty("pagina")
    private Integer page = 0;
}
