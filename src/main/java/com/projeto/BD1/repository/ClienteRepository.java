package com.projeto.BD1.repository;

import com.projeto.BD1.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
