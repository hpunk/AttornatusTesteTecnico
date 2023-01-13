package com.gaav.Attornatus.Teste.Backend.controller;

import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedFilter;
import com.gaav.Attornatus.Teste.Backend.domain.controller.base.PaginatedResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonBaseRequest;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonResponse;
import com.gaav.Attornatus.Teste.Backend.domain.controller.person.PersonUpdateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping("/teste/pessoa")
public interface PersonApi {

    @PostMapping
    PersonResponse createPerson(@Valid @RequestBody PersonBaseRequest personDto);

    @PutMapping
    PersonResponse editPerson(@Valid @RequestBody PersonUpdateRequest personDto);

    @GetMapping("/{pessoa}")
    PersonResponse getPerson(@NotNull @PathVariable("pessoa") UUID personId);

    @GetMapping
    PaginatedResponse<PersonResponse> listPeople(@Valid PaginatedFilter filter);
}
