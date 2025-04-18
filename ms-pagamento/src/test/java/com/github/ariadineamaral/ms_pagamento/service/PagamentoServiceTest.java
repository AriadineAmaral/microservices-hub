package com.github.ariadineamaral.ms_pagamento.service;

import com.github.ariadineamaral.ms_pagamento.dto.PagamentoDTO;
import com.github.ariadineamaral.ms_pagamento.entity.Pagamento;
import com.github.ariadineamaral.ms_pagamento.repository.PagamentoRepository;
import com.github.ariadineamaral.ms_pagamento.service.exceptions.ResourceNotFoundException;
import com.github.ariadineamaral.ms_pagamento.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService service;


    @Mock
    private PagamentoRepository repository;


    private Long existingId;
    private Long nonExistingId;
    private Pagamento pagamento;
    private PagamentoDTO pagamentoDTO;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 10L;
        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.doNothing().when(repository).deleteById(existingId);

        pagamento = Factory.createPagamento();
        pagamentoDTO = new PagamentoDTO(pagamento);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(pagamento));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(repository.save(any())).thenReturn(pagamento);
        Mockito.when(repository.getReferenceById(existingId)).thenReturn(pagamento);
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
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

    @Test
    @DisplayName("delete deveria lançar exceção ResourceNotFoundException quando id não existe")
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
            service.deletePagamento(nonExistingId);
                }
                );
    }

    @Test
    public void getByIdShouldReturnPagamentoDTOWhenIdExist(){
        PagamentoDTO dto = service.getById(existingId);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getId(), existingId);
        Assertions.assertEquals(dto.getValor(), pagamento.getValor());
    }

    @Test
    public void getByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
                    service.getById(nonExistingId);
                }
        );

    }

    @Test
    public void createPagamentoShouldReturnPagamentoDTOWhenPagamentoIsCreated(){
        PagamentoDTO dto = service.createPagamento(pagamentoDTO);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getId(), pagamento.getId());

    }

    @Test
    public void updatePagamentoShouldReturnPagamentoDTOWhenIdExist(){
        PagamentoDTO dto = service.updatePagamento(pagamento.getId(),pagamentoDTO);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getId(), existingId);
        Assertions.assertEquals(dto.getValor(), pagamento.getValor());
    }

    @Test
    public void updatePagamentoShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
                    service.updatePagamento(nonExistingId, pagamentoDTO);
                }
        );

    }


}
