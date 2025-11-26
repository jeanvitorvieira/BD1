package com.projeto.BD1.repository;

import com.projeto.BD1.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
