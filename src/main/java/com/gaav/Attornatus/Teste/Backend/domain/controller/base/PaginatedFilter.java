package com.gaav.Attornatus.Teste.Backend.domain.controller.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PaginatedFilter {

    @Min(value = 10, message = "O parametro filas deve ter valor de pelo menos 10")
    private Integer rows = 10;

    @Min(value = 0, message = "O parametro pagina deve ter valor de pelo menos 0")
    private Integer page = 0;

    @JsonProperty("page")
    public void setPagina(Integer page){
        this.page = page;
    }

    @JsonProperty("rows")
    public void setFilas(Integer rows) {
        this.rows = rows;
    }
}
