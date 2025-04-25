package com.github.ariadineamaral.ms_pedido.service;

import com.github.ariadineamaral.ms_pedido.dto.PedidoDTO;
import com.github.ariadineamaral.ms_pedido.entities.ItemDoPedido;
import com.github.ariadineamaral.ms_pedido.entities.Pedido;
import com.github.ariadineamaral.ms_pedido.entities.Status;
import com.github.ariadineamaral.ms_pedido.repositories.ItemDoPedidoRepository;
import com.github.ariadineamaral.ms_pedido.repositories.PedidoRepository;
import com.github.ariadineamaral.ms_pedido.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service

public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ItemDoPedidoRepository itemDoPedidoRepository;

    @Transactional(readOnly = true)
    public List<PedidoDTO> getAllPedidos(){
        return repository.findAll().stream().map(PedidoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public PedidoDTO getById(Long id){
        Pedido entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado" + id)
        );
        return new PedidoDTO(entity);
    }

    @Transactional
    public PedidoDTO savePedido (PedidoDTO dto){
        Pedido entity = new Pedido();
        entity.setData(LocalDate.now());
        entity.setStatus(Status.REALIZADO);
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        itemDoPedidoRepository.saveAll(entity.getItens());
        return new PedidoDTO(entity);

    }

    private void copyDtoToEntity(PedidoDTO dto, Pedido entity) {
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());

        List<ItemDoPedido> itens = new ArrayList<>();
        for ()

    }

}
