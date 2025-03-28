package com.github.ariadineamaral.ms_pagamento.service;

import com.github.ariadineamaral.ms_pagamento.repository.PagamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService service;


    @Mock
    private PagamentoRepository repository;


    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 10L;
        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.doNothing().when(repository).deleteById(existingId);
    }

    @Test
    @DisplayName("delete deveria não fazer nada quando id existe")
    public void deleteShouldDoNothingIdExists(){
        Assertions.assertDoesNotThrow(
                () ->
                {
                    service.deletePagamento(existingId);
                }
        );
    }

}
