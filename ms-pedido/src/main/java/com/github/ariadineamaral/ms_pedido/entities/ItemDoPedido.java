package com.github.ariadineamaral.ms_pedido.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_item_do_pedido")

public class ItemDoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Integer quatidade;
    private String descricao;
    private BigDecimal valorUnitario;
    

}
