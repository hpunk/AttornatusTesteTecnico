package com.gaav.Attornatus.Teste.Backend.domain.controller.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("estado")
    private int status;
    @JsonProperty("mensagem")
    private String message;
    @JsonProperty("detalhe")
    private String detail;
}
