package com.github.ariadineamaral.ms_pagamento.tests;

import com.github.ariadineamaral.ms_pagamento.entity.Pagamento;
import com.github.ariadineamaral.ms_pagamento.entity.Status;

import java.math.BigDecimal;

public class Factory {

    public static Pagamento createPagamento() {
        Pagamento pagamento = new Pagamento(1L, BigDecimal.valueOf(35.25),
                "Yasmin Chiquinha", "1234123412341234",
                "12/30", "123", Status.CRIADO,
                1L, 2L);
        return pagamento;
    }
}
