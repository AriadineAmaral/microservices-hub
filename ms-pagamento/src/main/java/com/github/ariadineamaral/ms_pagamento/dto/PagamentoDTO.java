package com.github.ariadineamaral.ms_pagamento.dto;

import com.github.ariadineamaral.ms_pagamento.entity.Pagamento;
import com.github.ariadineamaral.ms_pagamento.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {

    private Long id;
    @NotNull(message = "Campo Obrigatorio")
    @Positive(message = "O Valor do pagamento deve ser um numero positivo")
    private BigDecimal valor;

    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Size(min = 16, max = 19, message = "Número do cartao deve ter entre 16 e 19 caracteres")
    private String numeroDoCartao;

    @Size(min = 5, max = 5, message = "A validade do cartao deve ter 5 caracteres")
    private String validade;

    @Size(min = 3, max = 3, message = "o código de sergurança do cartao deve ter 3 caracteres")
    private String codigoDeSeguranca;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Pedido id é obrigatório")
    private Long pedidoId;

    @NotNull(message = "A forma de pagamento obrigatória")
    private Long formaDePagamentoId;


    public PagamentoDTO(Pagamento entity) {
        valor = entity.getValor();
        nome = entity.getNome();
        id = entity.getId();
        numeroDoCartao = entity.getNumeroDoCartao();
        validade = entity.getValidade();
        codigoDeSeguranca = entity.getCodigoDeSeguranca();
        status = entity.getStatus();
        pedidoId = entity.getPedidoId();
        formaDePagamentoId = entity.getFormaDePagamentoId();
    }
}
