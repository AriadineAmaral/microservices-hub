package com.github.ariadineamaral.ms_pedido.dto;

import com.github.ariadineamaral.ms_pedido.entities.ItemDoPedido;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class ItemDoPedidoDTO {

    private Long id;

    @NotNull(message = "Quantidade requerida")
    @Positive(message = "Quantidade deve ser um número positivo")
    private Integer quantidade;

    @NotEmpty(message = "Descrição requerido")
    private String descricao;

    @NotNull(message = "Valor unitário requerido")
    @Positive(message = "Valor unitário deve ser um número positivo")
    private BigDecimal valorUnitario;

    public ItemDoPedidoDTO(ItemDoPedido entity) {
        id = entity.getId();
        quantidade = entity.getQuantidade();
        descricao = entity.getDescricao();
        valorUnitario = entity.getValorUnitario();

    }

}
