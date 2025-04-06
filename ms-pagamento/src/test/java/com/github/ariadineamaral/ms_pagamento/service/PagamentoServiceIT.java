package com.github.ariadineamaral.ms_pagamento.service;


import com.github.ariadineamaral.ms_pagamento.repository.PagamentoRepository;
import com.github.ariadineamaral.ms_pagamento.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PagamentoServiceIT {

    @Autowired
    private PagamentoService service;

    @Autowired
    private PagamentoRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalPagamento;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        countTotalPagamento = 6L;
    }

    @Test
    public void deletePagamentoShouldDeleteResourceWhenIdExists() {

        service.deletePagamento(existingId);
        Assertions.assertEquals(countTotalPagamento - 1, repository.count());
    }

    @Test
    public void deletePagamentoShoulThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deletePagamento(nonExistingId);
                });
    }

    @Test
    public void getAllShouldReturnListPagamentoDTO() {
        var result = service.getAll();
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalPagamento, result.size());
        Assertions.assertEquals(Double.valueOf(35.25), result.get(0).getValor().doubleValue());
        Assertions.assertEquals("Yasmin Chiquinha", result.get(0).getNome());
        Assertions.assertEquals("Fantine Chiquinha", result.get(1).getNome());
        Assertions.assertEquals(null, result.get(5).getNome());

    }

}
