package com.github.ariadineamaral.ms_pedido.repositories;

import com.github.ariadineamaral.ms_pedido.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
