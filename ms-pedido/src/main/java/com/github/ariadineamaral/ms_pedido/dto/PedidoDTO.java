package com.github.ariadineamaral.ms_pedido.dto;

import com.github.ariadineamaral.ms_pedido.entities.ItemDoPedido;
import com.github.ariadineamaral.ms_pedido.entities.Pedido;
import com.github.ariadineamaral.ms_pedido.entities.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoDTO {

    private Long id;

    private String cpf;

    @NotEmpty(message = "Nome requerido")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotEmpty(message = "Deve ter pelo menos um item no pedido")
    private List<@Valid ItemDoPedidoDTO> itens = new ArrayList<>();

    public PedidoDTO(Pedido entity) {
        id = entity.getId();
        cpf = entity.getCpf();
        nome = entity.getNome();
        data = entity.getData();
        status = entity.getStatus();

        for (ItemDoPedido item : entity.getItens()) {
            ItemDoPedidoDTO itemDTO = new ItemDoPedidoDTO(item);
            itens.add(itemDTO);

        }
    }
}
