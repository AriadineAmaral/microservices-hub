package com.github.ariadineamaral.ms_pagamento.service;


import com.github.ariadineamaral.ms_pagamento.dto.PagamentoDTO;
import com.github.ariadineamaral.ms_pagamento.entity.Pagamento;
import com.github.ariadineamaral.ms_pagamento.entity.Status;
import com.github.ariadineamaral.ms_pagamento.repository.PagamentoRepository;
import com.github.ariadineamaral.ms_pagamento.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Transactional(readOnly = true)
    public List<PagamentoDTO> getAll() {
        List<Pagamento> pagamentos = repository.findAll();
        return pagamentos.stream().map(PagamentoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagamentoDTO getById(Long id){
        Pagamento entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado, ID: " + id)
        );
        return new PagamentoDTO(entity);
        }


        @Transactional
        public PagamentoDTO createPagamento(PagamentoDTO dto){
        Pagamento entity = new Pagamento();
        copyDtoEntity(dto, entity);
        entity.setStatus(Status.CRIADO);
        entity = repository.save(entity);
        return new PagamentoDTO(entity);
        }

    private void copyDtoEntity(PagamentoDTO dto, Pagamento entity) {

        entity.setValor(dto.getValor());
        entity.setNome(dto.getNome());
        entity.setNumeroDoCartao(dto.getNumeroDoCartao());
        entity.setValidade(dto.getValidade());
        entity.setCodigoDeSeguranca(dto.getCodigoDeSeguranca());
        entity.setPedidoId(dto.getPedidoId());
        entity.setFormaDePagamentoId(dto.getFormaDePagamentoId());
    }

    @Transactional
    public PagamentoDTO updatePagamento(Long id, PagamentoDTO dto){
        try {
            Pagamento entity = repository.getReferenceById(id);
            copyDtoEntity(dto,entity);
            entity.setStatus(dto.getStatus());
            entity = repository.save(entity);
            return new PagamentoDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado. Id:" + id);
        }
    }

    @Transactional
    public void  deletePagamento (Long id) {
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID:" + id);
        }
        repository.deleteById(id);
    }
}

