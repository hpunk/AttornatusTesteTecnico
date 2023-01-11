package com.gaav.Attornatus.Teste.Backend.domain.controller.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse <T> {
    @JsonProperty("pagina")
    private Integer page;
    @JsonProperty("filas")
    private Integer rows;
    @JsonProperty("dados")
    private List<T> data;
    @JsonProperty("quantidade")
    private Long count;
}
