package com.github.ariadineamaral.ms_pagamento.controller;


import com.github.ariadineamaral.ms_pagamento.dto.PagamentoDTO;
import com.github.ariadineamaral.ms_pagamento.entity.Pagamento;
import com.github.ariadineamaral.ms_pagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pagamentos")
public class PagamentoController {


    @Autowired
    private PagamentoService service;


    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> getAll() {
        List<PagamentoDTO> dto = service.getAll();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> getById(@PathVariable Long id) {
    PagamentoDTO dto = service.getById(id);
    return ResponseEntity.ok(dto);
    }



}
