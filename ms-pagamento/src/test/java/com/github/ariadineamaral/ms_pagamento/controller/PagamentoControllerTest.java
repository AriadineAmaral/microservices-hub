package com.github.ariadineamaral.ms_pagamento.controller;

import com.github.ariadineamaral.ms_pagamento.dto.PagamentoDTO;
import com.github.ariadineamaral.ms_pagamento.service.PagamentoService;
import com.github.ariadineamaral.ms_pagamento.service.exceptions.ResourceNotFoundException;
import com.github.ariadineamaral.ms_pagamento.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;
import java.net.URI;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest
public class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagamentoService service;
    private PagamentoDTO dto;
    private Long existingId;
    private Long nonExistisId;


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistisId = 100L;
        dto = Factory.createPagamentoDTO();

        List<PagamentoDTO> list = List.of(dto);

        Mockito.when(service.getAll()).thenReturn(list);
        Mockito.when(service.getById(existingId)).thenReturn(dto);
        Mockito.when(service.getById(nonExistisId)).thenThrow(ResourceNotFoundException.class);
        Mockito.when(service.createPagamento(any())).thenReturn(dto);
    }

    @Test
    public void getAllShouldReturnListPagamentoDTO() throws Exception {

        ResultActions result = mockMvc.perform(get("/pagamentos").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }


    @Test
    public void getByIdShouldReturnPagamentoDTOWhenIdExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/pagamentos/{id}").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.valor").exists());
        result.andExpect(jsonPath("$.status").exists());

    }

    @Test
    public void getByIdShouldReturnNotFoundExceptionWhenIdDoesNotExist() throws Exception{
        ResultActions result = mockMvc.perform(get("/pagamentos/{id}", nonExistisId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> create(@RequestBody @Valid PagamentoDTO dto) {
        dto = service.createPagamento(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Test
    public void createPagamentoShouldReturnPagamentoDTOCreated() throws Exception {
        PagamentoDTO newPagamentoDTO = Factory.createNewPagamentoDTO();
        String jsonRequestBody = objectMapper.writeValueAsString(newPagamentoDTO);
        mockMvc.perform(post("/pagamentos")
                .content(jsonRequestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.pedidoId").exists())
                .andExpect(jsonPath("$.formaDePagamentoId").exists());

    }

}
