package com.gaav.Attornatus.Teste.Backend.controller;

import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RequestMapping("/teste/pessoa")
public interface PersonApi {

    @PostMapping
    PersonResponse createPerson(@Valid @RequestBody PersonRequest personDto);

    @PutMapping
    PersonResponse editPerson(@Valid @RequestBody PersonRequest personDto);

    @GetMapping("/{pessoa}")
    PersonResponse getPerson(@NotNull @PathVariable("pessoa") UUID personId);

    @GetMapping
    PaginatedResponse<PersonResponse> listPeople(@Valid PaginatedFilter filter);
}
