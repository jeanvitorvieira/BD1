package com.projeto.BD1.repository;

import com.projeto.BD1.model.ContasReceber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContasReceberRepository extends JpaRepository<ContasReceber, Integer> {
}
